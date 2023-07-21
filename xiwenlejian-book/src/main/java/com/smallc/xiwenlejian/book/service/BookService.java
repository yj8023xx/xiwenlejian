package com.smallc.xiwenlejian.book.service;

import java.util.List;

import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import com.smallc.xiwenlejian.common.recommender.constant.RecModelType;

/**
 * 服务类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.book.service
 */
public interface BookService {

    BookVO getInfo(Long bookId);
    List<BookVO> listHighRatedBooks(Integer size);
    List<BookVO> listHistoryBooks(Long userId, Integer size);
    List<BookVO> listRecBooks(Long userId, Integer recSize, Integer recModel);
    List<BookVO> listSimilarBooks(Long bookId, Integer size, Integer model);

    BookBO getByBookId(Long bookId);
    List<BookBO> listByOrder(String orderBy, Integer size);
    List<BookBO> listByAuthor(String author, Integer size);
    List<BookBO> listByPublisher(String publisher, Integer size);
    List<BookBO> listByRated(Long userId, Integer size);

}
