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
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    /**
     * 书本详情
     *
     * @param bookId 书本的id
     * @return
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookVO> getInfo(@PathVariable("bookId") Long bookId) {
        BookVO bookVO = bookService.getInfo(bookId);
        if (bookVO == null) {
            return onFail(HttpStatus.NOT_FOUND.value(), "书籍未找到！");
        }
        return onSuccess(bookVO);
    }

    /**
     * 获取评分高的书
     *
     * @param size 书籍的数量
     * @return
     */
    @GetMapping("/highrated")
    public ResponseEntity<List<BookVO>> listHighRatedBooks(@RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listHighRatedBooks(size);
        return onSuccess(bookVOs);
    }

    /**
     * 获取用户看过的书
     *
     * @param userId 用户id
     * @param size   历史书籍的数量
     * @return
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BookVO>> listHistoryBooks(@PathVariable("userId") Long userId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listHistoryBooks(userId, size);
        return onSuccess(bookVOs);
    }

    /**
     * 猜你喜欢
     *
     * @param userId 用户id
     * @param size   推荐书籍的数量
     * @return
     */
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<List<BookVO>> listRecBooks(@PathVariable("userId") Long userId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> books = bookService.listRecBooks(userId, Constant.REC_MODEL.getValue(), size);
        return onSuccess(books);
    }

    /**
     * 查找相似书籍
     *
     * @param bookId 查看书籍id
     * @param size   推荐的相似书籍数量
     * @return
     */
    @GetMapping("/similar/{bookId}")
    public ResponseEntity<List<BookVO>> listSimilarBooks(@PathVariable("bookId") Long bookId, @RequestParam(required = false, defaultValue = "12") Integer size) {
        List<BookVO> bookVOs = bookService.listSimilarBooks(bookId, Constant.SIMILAR_MODEL.getValue(), size);
        return onSuccess(bookVOs);
    }

}
