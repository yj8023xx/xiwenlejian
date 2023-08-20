package com.smallc.xiwenlejian.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.smallc.xiwenlejian.api.recommender.feign.RecommenderFeignClient;
import com.smallc.xiwenlejian.book.dao.BookDao;
import com.smallc.xiwenlejian.book.dao.RatingDao;
import com.smallc.xiwenlejian.book.mapper.BookMapper;
import com.smallc.xiwenlejian.book.model.Book;
import com.smallc.xiwenlejian.book.service.BookService;
import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

/**
 * 服务实现类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.service.impl
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private RatingDao ratingDao;
    @Autowired
    private RecommenderFeignClient recommenderFeignClient;

    @Override
    public BookVO getInfo(Long bookId) {
        Book book = bookDao.getByBookId(bookId);
        return BookMapper.INSTANCE.convertToVO(book);
    }

    @Override
    public List<BookVO> listHighRatedBooks(Integer size) {
        String orderBy = "average_rating DESC";
        PageHelper.startPage(1, size, orderBy);
        List<Book> books = bookDao.listByOrder(orderBy);
        List<BookVO> bookVOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookVOs.add(BookMapper.INSTANCE.convertToVO(book));
        }
        return bookVOs;
    }

    @Override
    public List<BookVO> listHistoryBooks(Long userId, Integer size) {
        PageHelper.startPage(1, size);
        List<Long> bookIds = ratingDao.listHistoryBookIds(userId);
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> books = bookDao.listByBookIds(bookIds);
        List<BookVO> bookVOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookVOs.add(BookMapper.INSTANCE.convertToVO(book));
        }
        return bookVOs;
    }

    @Override
    public List<BookVO> listRecBooks(Long userId, Integer model, Integer size) {
        List<BookBO> bookBOs = recommenderFeignClient.listRecBooks(userId, model, size);
        List<BookVO> bookVOs = new ArrayList<>(bookBOs.size());
        for (BookBO bookBO : bookBOs) {
            bookVOs.add(BookMapper.INSTANCE.convertToVO(bookBO));
        }
        return bookVOs;
    }

    @Override
    public List<BookVO> listSimilarBooks(Long bookId, Integer model, Integer size) {
        List<BookBO> bookBOs = recommenderFeignClient.listSimilarBooks(bookId, model, size);
        List<BookVO> bookVOs = new ArrayList<>(bookBOs.size());
        for (BookBO bookBO : bookBOs) {
            bookVOs.add(BookMapper.INSTANCE.convertToVO(bookBO));
        }
        return bookVOs;
    }

    @Override
    public BookBO getByBookId(Long bookId) {
        Book book = bookDao.getByBookId(bookId);
        return BookMapper.INSTANCE.convertToBO(book);
    }

    @Override
    public List<BookBO> listByBookIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> books = bookDao.listByBookIds(ids);
        List<BookBO> bookBOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookBOs.add(BookMapper.INSTANCE.convertToBO(book));
        }
        return bookBOs;
    }

    @Override
    public List<BookBO> listByOrder(String orderBy, Integer size) {
        PageHelper.startPage(1, size, orderBy);
        List<Book> books = bookDao.listByOrder(orderBy);
        List<BookBO> bookBOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookBOs.add(BookMapper.INSTANCE.convertToBO(book));
        }
        return bookBOs;
    }

    @Override
    public List<BookBO> listByAuthor(String author, Integer size) {
        PageHelper.startPage(1, size);
        List<Book> books = bookDao.listByAuthor(author);
        List<BookBO> bookBOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookBOs.add(BookMapper.INSTANCE.convertToBO(book));
        }
        return bookBOs;
    }

    @Override
    public List<BookBO> listByPublisher(String publisher, Integer size) {
        PageHelper.startPage(1, size);
        List<Book> books = bookDao.listByPublisher(publisher);
        List<BookBO> bookBOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookBOs.add(BookMapper.INSTANCE.convertToBO(book));
        }
        return bookBOs;
    }

    @Override
    public List<BookBO> listByRecentHighRated(Long userId, Integer size) {
        PageHelper.startPage(1, size);
        List<Long> bookIds = ratingDao.listRecentHighRatedBookIds(userId);
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> books = bookDao.listByBookIds(bookIds);
        List<BookBO> bookBOs = new ArrayList<>(books.size());
        for (Book book : books) {
            bookBOs.add(BookMapper.INSTANCE.convertToBO(book));
        }
        return bookBOs;
    }

}
