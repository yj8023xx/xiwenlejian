package com.smallc.xiwenlejian.user.model;


import java.io.Serializable;

/**
 * 用户
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.user.model
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
