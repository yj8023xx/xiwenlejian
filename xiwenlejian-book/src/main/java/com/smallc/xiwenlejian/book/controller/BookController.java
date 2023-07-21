package com.smallc.xiwenlejian.book.controller;

import java.io.IOException;
import java.util.List;

import com.smallc.xiwenlejian.book.constant.Constant;
import com.smallc.xiwenlejian.book.service.BookService;
import com.smallc.xiwenlejian.common.core.controller.BaseController;
import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/info")
    public ResultVO getInfo(@RequestParam Long bookId) {
        BookVO bookVO = bookService.getInfo(bookId);
        return onSuccess(bookVO);
    }

    @GetMapping("/highrated")
    public ResultVO listHighRatedBooks(@RequestParam Integer size) {
        List<BookVO> bookVOs = bookService.listHighRatedBooks(size);
        return onSuccess(bookVOs);
    }

    @GetMapping("/history")
    public ResultVO listHistoryBooks(@RequestParam Long userId, @RequestParam Integer size) {
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
    @GetMapping("/rec")
    public ResultVO listRecBooks(@RequestParam Long userId, @RequestParam Integer size) {
        List<BookVO> books = bookService.listRecBooks(userId, size, Constant.REC_MODEL.getValue());
        return onSuccess(books);
    }

    /**
     * 查找相似书籍
     *
     * @param bookId
     * @return
     */
    @GetMapping("/similar")
    public ResultVO listSimilarBooks(@RequestParam Long bookId, @RequestParam Integer size) {
        List<BookVO> bookVOs = bookService.listSimilarBooks(bookId, size, Constant.SIMILAR_MODEL.getValue());
        return onSuccess(bookVOs);
    }

}
