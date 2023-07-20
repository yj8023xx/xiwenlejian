package com.smallc.xiwenlejian.common.book.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.common.book.bo
 */
public class BookBO {

    /**
     * 书的Id
     */
    private Long bookId;

    /**
     * 书的国际标准书号
     */
    private String ISBN;

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

    private List<Double> embedding;

    private Map<String, String> bookFeatures;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
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

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public Map<String, String> getBookFeatures() {
        return bookFeatures;
    }

    public void setBookFeatures(Map<String, String> bookFeatures) {
        this.bookFeatures = bookFeatures;
    }
}
