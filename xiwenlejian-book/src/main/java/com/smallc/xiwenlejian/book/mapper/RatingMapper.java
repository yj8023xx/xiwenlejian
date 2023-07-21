package com.smallc.xiwenlejian.book.mapper;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/16
 * @since com.smallc.xiwenlejian.book.mapper
 */
@Repository
public interface RatingMapper {

    List<Long> listHistoryBookIds(@RequestParam("userId") Long userId);

}
