package com.smallc.xiwenlejian.user.vo;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.user.vo
 */
public class UserVO {

    private Long userId;
    private String username;
    private Integer age;

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

}
