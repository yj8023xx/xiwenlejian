package com.smallc.xiwenlejian.search.dto;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/14
 * @since com.smallc.xiwenlejian.search.dto
 */
public class SearchDTO {

    Integer pageNum;
    Integer pageSize;
    String keyword;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
