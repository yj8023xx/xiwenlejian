package com.smallc.xiwenlejian.common.user.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.common.user.bo
 */
public class UserBO {

    private Long userId;
    private String username;
    private Integer age;
    private List<Double> embedding;
    private Map<String, String> userFeatures;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public Map<String, String> getUserFeatures() {
        return userFeatures;
    }

    public void setUserFeatures(Map<String, String> userFeatures) {
        this.userFeatures = userFeatures;
    }

}
