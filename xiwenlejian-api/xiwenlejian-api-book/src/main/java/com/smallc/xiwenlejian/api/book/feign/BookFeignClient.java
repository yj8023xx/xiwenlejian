package com.smallc.xiwenlejian.api.book.feign;

import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/feign/book/{bookId}")
    BookBO getByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/feign/book/list")
    List<BookBO> listByBookIds(@RequestBody List<Long> ids);

    @GetMapping("/feign/book/sorted")
    List<BookBO> listByOrder(@RequestParam("orderBy") String orderBy, @RequestParam("size") Integer size);

    @GetMapping("/feign/book/author/{author}")
    List<BookBO> listByAuthor(@PathVariable("author") String author, @RequestParam("size") Integer size);

    @GetMapping("/feign/book/publisher/{publisher}")
    List<BookBO> listByPublisher(@RequestParam("publisher") String publisher, @RequestParam("size") Integer size);

    @GetMapping("/feign/book/highrated/{userId}")
    List<BookBO> listRecentHighRatedBooks(@PathVariable("userId") Long userId, @RequestParam("size") Integer size);

}
