package com.smallc.xiwenlejian.recommender.online.utils;

import java.util.List;

public class SimilarityUtil {

    /**
     * calculate cosine similarity between two embeddings
     */
    public static double calculateCosineSimilarity(List<Double> e1, List<Double> e2) {
        if (null == e1 || null == e2 || e1.size() != e2.size()) {
            return -1;
        }
        double dotProduct = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        for (int i = 0; i < e1.size(); i++) {
            dotProduct += e1.get(i) * e2.get(i);
            denominator1 += e1.get(i) * e1.get(i);
            denominator2 += e2.get(i) * e2.get(i);
        }
        return dotProduct / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
    }

}
