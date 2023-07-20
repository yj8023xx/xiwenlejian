package com.smallc.xiwenlejian.api.recommender.feign;

import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/listRecBooks")
    List<BookBO> listRecBooks(@RequestParam("userId") Long userId, @RequestParam("recSize") Integer recSize, @RequestParam("recModel") Integer recModel);

    @GetMapping("/listSimilarBooks")
    List<BookBO> listSimilarBooks(@RequestParam("bookId") Long bookId, @RequestParam("recSize") Integer recSize, @RequestParam("recModel") Integer recModel);

}
