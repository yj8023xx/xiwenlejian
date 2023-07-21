package com.smallc.xiwenlejian.recommender.online.service;

import com.smallc.xiwenlejian.common.book.bo.BookBO;
import com.smallc.xiwenlejian.common.recommender.constant.RecModelType;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/16
 * @since com.smallc.xiwenlejian.recommender.online.service
 */
public interface RecommenderService {

    List<BookBO> listRecBooks(Long userId, Integer size, Integer model);
    List<BookBO> listSimilarBooks(Long bookId, Integer size, Integer model);

}
