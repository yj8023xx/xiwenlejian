package com.smallc.xiwenlejian.book.model;

import java.io.Serializable;

/**
 * 书籍
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.entity
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 书的Id
     */
    private Long bookId;

    /**
     * 书的国际标准书号
     */
    private String isbn;

    /**
     * 书的标题
     */
    private String title;

    /**
     * 书的作者
     */
    private String author;

    /**
     * 书的发行年份
     */
    private Integer releaseYear;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 书的平均评分
     */
    private Double averageRating;

    /**
     * 书的图片路径
     */
    private String imageUrlS;

    private String imageUrlM;

    private String imageUrlL;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double average_rating) {
        this.averageRating = average_rating;
    }

    public String getImageUrlS() {
        return imageUrlS;
    }

    public void setImageUrlS(String imageUrlS) {
        this.imageUrlS = imageUrlS;
    }

    public String getImageUrlM() {
        return imageUrlM;
    }

    public void setImageUrlM(String imageUrlM) {
        this.imageUrlM = imageUrlM;
    }

    public String getImageUrlL() {
        return imageUrlL;
    }

    public void setImageUrlL(String imageUrlL) {
        this.imageUrlL = imageUrlL;
    }

}
