package com.daojia.hockday.entity;

import java.io.Serializable;

/**
 * @author Dawei 2018/11/17
 */
public class UserInfo implements Serializable {

    private Long id;

    private String userName;

    private String photoUrl;

    /* 微信头像---- url 作为唯一标识 */
    private String token;

    private String md5key;

    public UserInfo() {
    }

    public UserInfo(Long id, String userName, String photoUrl, String token, String md5key) {
        this.id = id;
        this.userName = userName;
        this.photoUrl = photoUrl;
        this.token = token;
        this.md5key = md5key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMd5key() {
        return md5key;
    }

    public void setMd5key(String md5key) {
        this.md5key = md5key;
    }
}
