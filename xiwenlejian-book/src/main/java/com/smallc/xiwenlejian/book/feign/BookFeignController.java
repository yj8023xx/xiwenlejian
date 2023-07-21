package com.smallc.xiwenlejian.book.feign;

import com.smallc.xiwenlejian.api.book.feign.BookFeignClient;
import com.smallc.xiwenlejian.book.service.BookService;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/11
 * @since com.smallc.xiwenlejian.book.feign
 */
@RestController
public class BookFeignController implements BookFeignClient {

    @Autowired
    private BookService bookService;

    @Override
    public BookBO getByBookId(Long bookId) {
        return bookService.getByBookId(bookId);
    }

    @Override
    public List<BookBO> listByOrder(String orderBy, Integer size) {
        return bookService.listByOrder(orderBy, size);
    }

    @Override
    public List<BookBO> listByAuthor(String author, Integer size) {
        return bookService.listByAuthor(author, size);
    }

    @Override
    public List<BookBO> listByPublisher(String publisher, Integer size) {
        return bookService.listByPublisher(publisher, size);
    }

    @Override
    public List<BookBO> listHistoryBooks(Long userId, Integer size) {
        return bookService.listByRated(userId, size);
    }

}
