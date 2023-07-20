package com.smallc.xiwenlejian.search.vo;

import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/14
 * @since com.smallc.xiwenlejian.search.vo
 */
public class BookVO {

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
    private String releaseYear;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 书的类别
     */
    private List<String> category;

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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
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

}
