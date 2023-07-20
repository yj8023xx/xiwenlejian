package com.smallc.xiwenlejian.common.core.vo;

import java.io.Serializable;
import java.util.List;

public class PageResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> data;

    public PageResultVO(Integer totalPages, Long total, List<T> data) {
        this.totalPages = totalPages;
        this.total = total;
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
