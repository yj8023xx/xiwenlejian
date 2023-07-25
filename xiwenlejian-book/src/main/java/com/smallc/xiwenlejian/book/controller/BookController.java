package com.smallc.xiwenlejian.book.controller;

import java.io.IOException;
import java.util.List;

import com.smallc.xiwenlejian.book.constant.Constant;
import com.smallc.xiwenlejian.book.service.BookService;
import com.smallc.xiwenlejian.common.core.controller.BaseController;
import com.smallc.xiwenlejian.book.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.book.controller
 */
@RestController
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    /**
     * 书本详情
     *
     * @param bookId 书本的id
     * @return
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookVO> getInfo(@PathVariable Long bookId) {
        BookVO bookVO = bookService.getInfo(bookId);
        if (bookVO == null) {
            return onFail(HttpStatus.NOT_FOUND.value(), "书籍未找到！");
        }
        return onSuccess(bookVO);
    }

    /**
     * 获取评分高的书
     *
     * @param size
     * @return
     */
    @GetMapping("/book/highrated")
    public ResponseEntity<List<BookVO>> listHighRatedBooks(@RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listHighRatedBooks(size);
        return onSuccess(bookVOs);
    }

    /**
     * 获取用户看过的书
     *
     * @param userId
     * @param size
     * @return
     */
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<BookVO>> listHistoryBooks(@PathVariable Long userId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listHistoryBooks(userId, size);
        return onSuccess(bookVOs);
    }

    /**
     * 猜你喜欢
     *
     * @param userId
     * @return
     * @throws IOException
     */
    @GetMapping("/user/{userId}/rec")
    public ResponseEntity<List<BookVO>> listRecBooks(@PathVariable Long userId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> books = bookService.listRecBooks(userId, size, Constant.REC_MODEL.getValue());
        return onSuccess(books);
    }

    /**
     * 查找相似书籍
     *
     * @param bookId   查看书籍id
     * @param size 推荐的相似书籍数量
     * @return
     */
    @GetMapping("/book/{bookId}/similar")
    public ResponseEntity<List<BookVO>> listSimilarBooks(@PathVariable Long bookId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listSimilarBooks(bookId, size, Constant.SIMILAR_MODEL.getValue());
        return onSuccess(bookVOs);
    }

}
