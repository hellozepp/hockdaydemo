package com.daojia.hockday.entity;

import java.util.Date;

public class ArticleOperate {
    private Long id;

    private Long articleId;

    private Long userId;

    private Integer operateType;

    private Date operateTime;

    public ArticleOperate(Long id, Long articleId, Long userId, Integer operateType, Date operateTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.operateType = operateType;
        this.operateTime = operateTime;
    }

    public ArticleOperate() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}