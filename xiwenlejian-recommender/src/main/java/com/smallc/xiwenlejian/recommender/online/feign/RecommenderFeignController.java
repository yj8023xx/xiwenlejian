package com.smallc.xiwenlejian.recommender.online.feign;

import com.smallc.xiwenlejian.api.recommender.feign.RecommenderFeignClient;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import com.smallc.xiwenlejian.recommender.online.service.RecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.recommender.online.feign
 */
@RestController
public class RecommenderFeignController implements RecommenderFeignClient {

    @Autowired
    private RecommenderService recommenderService;

    @Override
    public List<BookBO> listRecBooks(Long userId, Integer model, Integer size) {
        return recommenderService.listRecBooks(userId, model, size);
    }

    @Override
    public List<BookBO> listSimilarBooks(Long bookId, Integer model, Integer size) {
        return recommenderService.listSimilarBooks(bookId, model, size);
    }

}
