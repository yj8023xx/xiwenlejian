package com.smallc.xiwenlejian.recommender.online.service;

import com.smallc.xiwenlejian.common.book.bo.BookBO;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/16
 * @since com.smallc.xiwenlejian.recommender.online.service
 */
public interface RecommenderService {

    List<BookBO> listRecBooks(Long userId, Integer model, Integer size);

    List<BookBO> listSimilarBooks(Long bookId, Integer model, Integer size);

}
