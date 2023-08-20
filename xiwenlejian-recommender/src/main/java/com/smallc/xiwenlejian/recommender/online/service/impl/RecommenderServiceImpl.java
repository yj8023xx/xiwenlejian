package com.smallc.xiwenlejian.recommender.online.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smallc.xiwenlejian.api.book.feign.BookFeignClient;
import com.smallc.xiwenlejian.api.user.feign.UserFeignClient;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import com.smallc.xiwenlejian.common.recommender.constant.ModelType;
import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.recommender.online.contsant.Constant;
import com.smallc.xiwenlejian.recommender.online.service.RecommenderService;
import com.smallc.xiwenlejian.recommender.online.utils.SimilarityUtil;
import io.milvus.client.MilvusClient;
import io.milvus.client.SearchParam;
import io.milvus.client.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/16
 * @since com.smallc.xiwenlejian.recommender.online.service.impl
 */
@Service
public class RecommenderServiceImpl implements RecommenderService {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private MilvusClient client;
    @Autowired
    private BookFeignClient bookFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;

    private List<Double> deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        bis.close();
        return (List<Double>) obj;
    }

    private List<Double> loadEmbedding(String key) {
        ArrayList<Double> embedding = null;
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] embeddingBytes = jedis.get(key.getBytes());
            if (null != embeddingBytes) {
                embedding = (ArrayList<Double>) deserialize(embeddingBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return embedding;
    }

    /**
     * get recommendation book list
     *
     * @param userId input user id
     * @param size   size of similar items
     * @param model  model used for calculating similarity
     * @return list of similar books
     */
    @Override
    public List<BookBO> listRecBooks(Long userId, Integer model, Integer size) {
        UserBO user = userFeignClient.getByUserId(userId);
        if (null == user) {
            return new ArrayList<>();
        }

        // load user embedding from redis
        user.setEmbedding(loadEmbedding("U" + userId));

        // TODO load user features from redis


        // step1: retrieval
        List<BookBO> candidates = multipleRetrievalCandidates(user, Constant.CANDIDATE_SIZE);
        // step2: rank
        List<BookBO> rankedList = ranker(user, candidates, model);

        if (rankedList.size() > size) {
            return rankedList.subList(0, size);
        }
        return rankedList;
    }


    /**
     * get recommendation book list
     *
     * @param bookId input book id
     * @param size   size of similar items
     * @param model  model used for calculating similarity
     * @return list of similar books
     */
    @Override
    public List<BookBO> listSimilarBooks(Long bookId, Integer model, Integer size) {
        BookBO book = bookFeignClient.getByBookId(bookId);
        if (null == book) {
            return new ArrayList<>();
        }

        // load book embedding from redis
        book.setEmbedding(loadEmbedding("I" + bookId));

        // TODO load book features from redis

        // step1: retrieval
        List<BookBO> candidates = multipleRetrievalCandidates(book, Constant.CANDIDATE_SIZE);
        // step2: rank
        List<BookBO> rankedList = ranker(book, candidates, model);

        if (rankedList.size() > size) {
            return rankedList.subList(0, size);
        }
        return rankedList;
    }

    public List<BookBO> multipleRetrievalCandidates(UserBO user, Integer candidateSize) {
        if (null == user) {
            return null;
        }

        Integer wayNum = 1;
        Integer candidateSizePerWay = candidateSize / wayNum;
        HashMap<Long, BookBO> candidateMap = new HashMap<>();

        // 基于历史行为的召回
        List<BookBO> historyBooks = bookFeignClient.listRecentHighRatedBooks(user.getUserId(), 10);
        if (!historyBooks.isEmpty()) {
            Integer candidateSizePerBook = candidateSizePerWay / historyBooks.size();
            for (BookBO book : historyBooks) {
                List<BookBO> retrievalBooks = multipleRetrievalCandidates(book, candidateSizePerBook);
                for (BookBO candidate : retrievalBooks) {
                    candidateMap.put(candidate.getBookId(), candidate);
                }
            }
        } else {
            // 基于规则的召回
            List<BookBO> highRatedCandidates = bookFeignClient.listByOrder("average_rating DESC", candidateSizePerWay);
            for (BookBO candidate : highRatedCandidates) {
                candidateMap.put(candidate.getBookId(), candidate);
            }
        }

        // TODO 基于协同过滤的召回


        // 基于Embedding的召回
//        List<BookBO> embeddingCandidates = retrievalCandidatesByEmbedding(user.getEmbedding(), candidateSizePerWay);
//        for (BookBO candidate : embeddingCandidates) {
//            candidateMap.put(candidate.getBookId(), candidate);
//        }

        for (BookBO book : historyBooks) {
            candidateMap.remove(book.getBookId());
        }
        return new ArrayList<>(candidateMap.values());
    }

    /**
     * multiple-retrieval candidate generation method
     *
     * @param book input book object
     * @return book candidates
     */
    public List<BookBO> multipleRetrievalCandidates(BookBO book, Integer candidateSize) {
        if (null == book) {
            return null;
        }

        Integer wayNum = 3;
        Integer candidateSizePerWay = candidateSize / wayNum;
        HashMap<Long, BookBO> candidateMap = new HashMap<>();

        // 基于规则的召回
        List<BookBO> authorCandidates = bookFeignClient.listByAuthor(book.getAuthor(), candidateSizePerWay);
        for (BookBO candidate : authorCandidates) {
            candidateMap.put(candidate.getBookId(), candidate);
        }

        List<BookBO> publisherCandidates = bookFeignClient.listByPublisher(book.getPublisher(), candidateSizePerWay);
        for (BookBO candidate : publisherCandidates) {
            candidateMap.put(candidate.getBookId(), candidate);
        }

        List<BookBO> highRatedCandidates = bookFeignClient.listByOrder("average_rating DESC", candidateSizePerWay);
        for (BookBO candidate : highRatedCandidates) {
            candidateMap.put(candidate.getBookId(), candidate);
        }

        // 基于Embedding的召回
        List<BookBO> embeddingCandidates = retrievalCandidatesByEmbedding(book.getEmbedding(), 6);
        for (BookBO candidate : embeddingCandidates) {
            candidateMap.put(candidate.getBookId(), candidate);
        }

        candidateMap.remove(book.getBookId());

        return new ArrayList<>(candidateMap.values());
    }

    /**
     * embedding based candidate generation method
     *
     * @param embedding user or item embedding
     * @param size      size of candidate pool
     * @return book candidates
     */
    public List<BookBO> retrievalCandidatesByEmbedding(List<Double> embedding, Integer size) {
        List<BookBO> candidates = new ArrayList<>();
        if (null == embedding) {
            return candidates;
        }
        // Search vectors
        // convert to float list
        List<Float> embeddingFloatList = embedding.stream()
                .map(Double::floatValue) // 使用map方法将Double转换为Float
                .collect(Collectors.toList());
        List<List<Float>> vectorsToSearch = new ArrayList<>(Collections.singleton(embeddingFloatList));
        // build search param
        JSONObject searchParamsJson = new JSONObject();
        searchParamsJson.put("nprobe", 20);
        SearchParam searchParam =
                new SearchParam.Builder("item_embedding")
                        .withFloatVectors(vectorsToSearch)
                        .withTopK(size)
                        .withParamsInJson(searchParamsJson.toString())
                        .build();
        SearchResponse searchResponse = client.search(searchParam);
        if (searchResponse.ok()) {
            List<List<Long>> resultIds = searchResponse.getResultIdsList();
            List<Long> bookIds = resultIds.get(0);
            if (!bookIds.isEmpty()) {
                candidates = bookFeignClient.listByBookIds(bookIds);
            }
        }

        return candidates.subList(0, Math.min(candidates.size(), size));
    }

    /**
     * rank candidates
     *
     * @param user       input user
     * @param candidates book candidates
     * @param model      model name used for ranking
     * @return ranked book list
     */
    public List<BookBO> ranker(UserBO user, List<BookBO> candidates, Integer model) {
        HashMap<BookBO, Double> candidateScoreMap = new HashMap<>();
        ModelType type = ModelType.getType(model);
        if (null == user.getEmbedding() && type == ModelType.EMBEDDING) {
            type = ModelType.DEFAULT;
        }
        switch (type) {
            case EMBEDDING:
                for (BookBO candidate : candidates) {
                    double similarity = calculateEmbSimilarScore(user, candidate);
                    candidateScoreMap.put(candidate, similarity);
                }
                break;
            case NCF:
                callNCFTorchServe(user, candidates, candidateScoreMap);
                break;
            case DEFAULT:
            default:
                // default ranking in candidate set
                for (int i = 0; i < candidates.size(); i++) {
                    candidateScoreMap.put(candidates.get(i), (double) (candidates.size() - i));
                }
        }

        List<BookBO> rankedList = new ArrayList<>();
        candidateScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(m -> rankedList.add(m.getKey()));
        return rankedList;
    }

    /**
     * call torchserve to get the NeuralCF model inference result
     *
     * @param user              input user
     * @param candidates        candidate books
     * @param candidateScoreMap save prediction score into the score map
     */
    public void callNCFTorchServe(UserBO user, List<BookBO> candidates, HashMap<BookBO, Double> candidateScoreMap) {
        if (null == user || null == candidates || candidates.size() == 0) {
            return;
        }

        JSONArray instances = new JSONArray();
        for (BookBO book : candidates) {
            JSONObject instance = new JSONObject();
            instance.put("user_id", user.getUserId());
            instance.put("book_id", book.getBookId());
            instances.add(instance);
        }

        JSONObject instancesRoot = new JSONObject();
        instancesRoot.put("data", instances);

        // need to confirm the torchserve end point
        String predictionScores = HttpUtil.post(Constant.NCF_MODEL_URL, instancesRoot.toString());

        JSONArray scores = JSONArray.parseArray(predictionScores);
        for (int i = 0; i < candidates.size(); i++) {
            candidateScoreMap.put(candidates.get(i), scores.getDouble(i));
        }
    }

    /**
     * rank candidates
     *
     * @param book       input book
     * @param candidates book candidates
     * @param model      model name used for ranking
     * @return ranked book list
     */
    public List<BookBO> ranker(BookBO book, List<BookBO> candidates, Integer model) {
        HashMap<BookBO, Double> candidateScoreMap = new HashMap<>();
        ModelType type = ModelType.getType(model);
        if (null == book.getEmbedding() && type == ModelType.EMBEDDING) {
            type = ModelType.DEFAULT;
        }
        switch (type) {
            case EMBEDDING:
                // calculate similarity
                for (BookBO candidate : candidates) {
                    double similarity = calculateEmbSimilarScore(book, candidate);
                    candidateScoreMap.put(candidate, similarity);
                }
                break;
            case DEFAULT:
            default:
                for (BookBO candidate : candidates) {
                    double similarity = calculateSimilarScore(book, candidate);
                    candidateScoreMap.put(candidate, similarity);
                }
        }

        List<BookBO> rankedList = new ArrayList<>();
        candidateScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(m -> rankedList.add(m.getKey()));
        return rankedList;
    }

    /**
     * function to calculate similarity score between user and book based on embedding
     *
     * @param user      input user
     * @param candidate candidate book
     * @return similarity score
     */
    public double calculateEmbSimilarScore(UserBO user, BookBO candidate) {
        if (null == user || null == candidate) {
            return -1;
        }
        // load embedding from redis if null
        if (null == candidate.getEmbedding()) {
            candidate.setEmbedding(loadEmbedding("I" + candidate.getBookId()));
        }
        return SimilarityUtil.calculateCosineSimilarity(user.getEmbedding(), candidate.getEmbedding());
    }

    /**
     * function to calculate similarity score between books based on embedding
     *
     * @param book      input book
     * @param candidate candidate book
     * @return similarity score
     */
    public double calculateEmbSimilarScore(BookBO book, BookBO candidate) {
        if (null == book || null == candidate) {
            return -1;
        }
        // load embedding from redis if null
        if (null == candidate.getEmbedding()) {
            candidate.setEmbedding(loadEmbedding("I" + candidate.getBookId()));
        }
        return SimilarityUtil.calculateCosineSimilarity(book.getEmbedding(), candidate.getEmbedding());
    }

    /**
     * function to calculate similarity score between books
     *
     * @param book      input book
     * @param candidate candidate book
     * @return similarity score
     */
    public double calculateSimilarScore(BookBO book, BookBO candidate) {
        int sameFeatureCount = 0;
        if (book.getPublisher().equals(candidate.getPublisher())) {
            sameFeatureCount++;
        }
        if (book.getAuthor().equals(candidate.getAuthor())) {
            sameFeatureCount++;
        }
        if (book.getReleaseYear().equals(candidate.getReleaseYear())) {
            sameFeatureCount++;
        }

        double titleSimilarity = StrUtil.similar(book.getTitle(), candidate.getTitle());
        double featureSimilarity = ((double) sameFeatureCount / 3 + titleSimilarity) / 2;
        double ratingScore = candidate.getAverageRating() / 10;

        double similarityWeight = 0.7;
        double ratingScoreWeight = 0.3;

        return featureSimilarity * similarityWeight + ratingScore * ratingScoreWeight;
    }

}
