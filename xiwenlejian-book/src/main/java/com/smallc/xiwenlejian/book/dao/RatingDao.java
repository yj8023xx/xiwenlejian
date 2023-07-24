package com.smallc.xiwenlejian.book.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/16
 * @since com.smallc.xiwenlejian.book.dao
 */
@Repository
public interface RatingDao {

    List<Long> listHistoryBookIds(@RequestParam("userId") Long userId);

    List<Long> listRecentHighRatedBookIds(@RequestParam("userId") Long userId);

}
