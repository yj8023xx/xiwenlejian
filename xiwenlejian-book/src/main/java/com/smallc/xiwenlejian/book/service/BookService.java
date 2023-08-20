package com.smallc.xiwenlejian.book.service;

import java.util.List;

import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.book.bo.BookBO;

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

    List<BookVO> listRecBooks(Long userId, Integer model, Integer size);

    List<BookVO> listSimilarBooks(Long bookId, Integer model, Integer size);

    BookBO getByBookId(Long bookId);

    List<BookBO> listByBookIds(List<Long> ids);

    List<BookBO> listByOrder(String orderBy, Integer size);

    List<BookBO> listByAuthor(String author, Integer size);

    List<BookBO> listByPublisher(String publisher, Integer size);

    List<BookBO> listByRecentHighRated(Long userId, Integer size);

}
