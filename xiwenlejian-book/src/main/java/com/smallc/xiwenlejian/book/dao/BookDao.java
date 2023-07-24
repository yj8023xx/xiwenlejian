package com.smallc.xiwenlejian.book.dao;

import java.util.List;

import com.smallc.xiwenlejian.book.model.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.book.dao
 */
@Repository
public interface BookDao {

    Book getByBookId(@Param("bookId") Long bookId);

    List<Book> listByBookIds(@Param("ids") List<Long> ids);

    List<Book> listByOrder(@Param("orderBy") String orderBy);

    List<Book> listByAuthor(@Param("author") String author);

    List<Book> listByPublisher(@Param("publisher") String publisher);

}
