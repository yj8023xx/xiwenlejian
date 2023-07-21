package com.smallc.xiwenlejian.book.model;

import java.io.Serializable;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/15
 * @since com.smallc.xiwenlejian.user.model
 */
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bookId;
    private Long userId;
    private Float score;
    private Long timestamp;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
