package com.smallc.xiwenlejian.book.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smallc.xiwenlejian.api.recommender.feign.RecommenderFeignClient;
import com.smallc.xiwenlejian.book.mapper.BookMapper;
import com.smallc.xiwenlejian.book.mapper.RatingMapper;
import com.smallc.xiwenlejian.book.model.Book;
import com.smallc.xiwenlejian.book.service.BookService;
import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.springframework.beans.BeanUtils;
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
    private BookMapper bookMapper;
    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private RecommenderFeignClient recommenderFeignClient;

    @Override
    public BookVO getInfo(Long bookId) {
        Book bookDO = bookMapper.getByBookId(bookId);
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookDO, bookVO);
        return bookVO;
    }

    private <S, T> void copyPropertiesList(List<S> sourceList, List<T> targetList, Class<T> targetClass) {
        for (S source : sourceList) {
            try {
                T target = targetClass.newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<BookVO> listHighRatedBooks(Integer size) {
        String orderBy = "average_rating DESC";
        PageHelper.startPage(1, size, orderBy);
        List<Book> bookDOs = bookMapper.listByOrder(orderBy);
        List<BookVO> bookVOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookVOs, BookVO.class);
        return bookVOs;
    }

    @Override
    public List<BookVO> listHistoryBooks(Long userId, Integer size) {
        PageHelper.startPage(1, size);
        List<Long> bookIds = ratingMapper.listHistoryBookIds(userId);
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> bookDOs = bookMapper.listByBookIds(bookIds);
        List<BookVO> bookVOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookVOs, BookVO.class);
        return bookVOs;
    }

    @Override
    public List<BookVO> listRecBooks(Long userId, Integer recSize, Integer recModel) {
        List<BookBO> bookBOs = recommenderFeignClient.listRecBooks(userId, recSize, recModel);
        List<BookVO> bookVOs = new ArrayList<>(bookBOs.size());
        copyPropertiesList(bookBOs, bookVOs, BookVO.class);
        return bookVOs;
    }

    @Override
    public List<BookVO> listSimilarBooks(Long bookId, Integer size, Integer model) {
        List<BookBO> bookBOs = recommenderFeignClient.listSimilarBooks(bookId, size, model);
        List<BookVO> bookVOs = new ArrayList<>(bookBOs.size());
        copyPropertiesList(bookBOs, bookVOs, BookVO.class);
        return bookVOs;
    }

    @Override
    public BookBO getByBookId(Long bookId) {
        Book bookDO = bookMapper.getByBookId(bookId);
        BookBO bookBO = new BookBO();
        BeanUtils.copyProperties(bookDO, bookBO);
        return bookBO;
    }

    @Override
    public List<BookBO> listByOrder(String orderBy, Integer size) {
        PageHelper.startPage(1, size, orderBy);
        List<Book> bookDOs = bookMapper.listByOrder(orderBy);
        List<BookBO> bookBOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookBOs, BookBO.class);
        return bookBOs;
    }

    @Override
    public List<BookBO> listByAuthor(String author, Integer size) {
        PageHelper.startPage(1, size);
        List<Book> bookDOs = bookMapper.listByAuthor(author);
        List<BookBO> bookBOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookBOs, BookBO.class);
        return bookBOs;
    }

    @Override
    public List<BookBO> listByPublisher(String publisher, Integer size) {
        PageHelper.startPage(1, size);
        List<Book> bookDOs = bookMapper.listByPublisher(publisher);
        List<BookBO> bookBOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookBOs, BookBO.class);
        return bookBOs;
    }

    @Override
    public List<BookBO> listByRated(Long userId, Integer size) {
        PageHelper.startPage(1, size);
        List<Long> bookIds = ratingMapper.listHistoryBookIds(userId);
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> bookDOs = bookMapper.listByBookIds(bookIds);
        List<BookBO> bookBOs = new ArrayList<>(bookDOs.size());
        copyPropertiesList(bookDOs, bookBOs, BookBO.class);
        return bookBOs;
    }

}
