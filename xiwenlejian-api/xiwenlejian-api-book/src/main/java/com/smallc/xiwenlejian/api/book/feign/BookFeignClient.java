package com.smallc.xiwenlejian.api.book.feign;

import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.api.book.feign
 */
@FeignClient("xiwenlejian-book")
public interface BookFeignClient {

    @GetMapping("/getByBookId")
    BookBO getByBookId(@RequestParam("bookId") Long bookId);

    @GetMapping("/listByOrder")
    List<BookBO> listByOrder(@RequestParam("orderBy") String orderBy, @RequestParam("size") Integer size);

    @GetMapping("/listByAuthor")
    List<BookBO> listByAuthor(@RequestParam("author") String author, @RequestParam("size") Integer size);

    @GetMapping("/listByPublisher")
    List<BookBO> listByPublisher(@RequestParam("publisher") String publisher, @RequestParam("size") Integer size);

    @GetMapping("/listHistoryBooks")
    List<BookBO> listHistoryBooks(@RequestParam("userId") Long userId, @RequestParam("size") Integer size);

}
