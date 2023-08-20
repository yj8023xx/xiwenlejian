package com.smallc.xiwenlejian.api.recommender.feign;

import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.api.recommender.feign
 */
@FeignClient("xiwenlejian-recommender")
public interface RecommenderFeignClient {

    @GetMapping("/feign/book/recommend/{userId}")
    List<BookBO> listRecBooks(@PathVariable("userId") Long userId, @RequestParam("model") Integer model, @RequestParam("size") Integer size);

    @GetMapping("/feign/book/similar/{bookId}")
    List<BookBO> listSimilarBooks(@PathVariable("bookId") Long bookId, @RequestParam("model") Integer model, @RequestParam("size") Integer size);

}
