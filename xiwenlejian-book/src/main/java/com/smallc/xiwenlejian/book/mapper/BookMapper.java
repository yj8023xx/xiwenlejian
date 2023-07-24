package com.smallc.xiwenlejian.book.mapper;

import com.smallc.xiwenlejian.book.model.Book;
import com.smallc.xiwenlejian.book.vo.BookVO;
import com.smallc.xiwenlejian.common.book.bo.BookBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/23
 * @since com.smallc.xiwenlejian.book.mapper
 */
@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookVO convertToVO(Book book);

    BookVO convertToVO(BookBO book);

    BookBO convertToBO(Book book);

}
