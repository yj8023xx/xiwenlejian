package com.smallc.xiwenlejian.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smallc.xiwenlejian.common.core.vo.PageResultVO;
import com.smallc.xiwenlejian.search.dto.SearchDTO;
import com.smallc.xiwenlejian.search.service.SearchService;
import com.smallc.xiwenlejian.search.vo.BookVO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.service.impl
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<BookVO> search(String keyword, Integer pageNum, Integer pageSize) throws IOException {
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("title", String.format("*%s*", keyword)));
        searchSourceBuilder.from(pageNum * pageSize);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 解析搜索结果
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<BookVO> books = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 根据索引映射将搜索结果转换为BookVO对象
            BookVO book = convertToBookVO(sourceAsMap);
            books.add(book);
        }

        return books;
    }

    private BookVO convertToBookVO(Map<String, Object> source) {
        BookVO book = new BookVO();
        // 根据source中的字段设置BookVO对象的属性
        book.setBookId(((Integer)source.get("book_id")).longValue());
        book.setISBN((String) source.get("isbn"));
        book.setTitle((String) source.get("title"));
        book.setAuthor((String) source.get("author"));
        Double averageRating = (Double) source.get("average_rating");
        book.setAverageRating(Math.round(averageRating * 10) / 10D);
        book.setImageUrlL((String) source.get("image_url_l"));
        return book;
    }

    @Override
    public PageResultVO<BookVO> searchBooks(SearchDTO searchDTO) {
        String keyword = searchDTO.getKeyword();
        Integer pageNum = searchDTO.getPageNum();
        Integer pageSize = searchDTO.getPageSize();

        try {
            List<BookVO> bookVOs = search(keyword, pageNum, pageSize);
            long totalResults = bookVOs.size();
            return new PageResultVO<>((int) Math.ceil(totalResults * 1.0 / pageSize), totalResults, bookVOs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
