package com.daojia.hockday.entity;

import java.util.Date;

public class CommentLink {
    private Long id;

    /* 文章ID */
    private Long articleId;

    /* 受评价人 */
    private Long acceptorId;

    /* 评价人 */
    private Long criticismId;

    /* 文章作者 */
    private Long authorId;

    /* 点赞数 */
    private Integer likeNum;
    /* 评价内容 */
    private String criticismContent;

    /* 创建时间 */
    private Date createTime;

    public CommentLink(Long id, Long articleId, Long acceptorId, Long criticismId, Long authorId, String criticismContent, Date createTime) {
        this.id = id;
        this.articleId = articleId;
        this.acceptorId = acceptorId;
        this.criticismId = criticismId;
        this.authorId = authorId;
        this.criticismContent = criticismContent;
        this.createTime = createTime;
    }

    public CommentLink() {
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

    public Long getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(Long acceptorId) {
        this.acceptorId = acceptorId;
    }

    public Long getCriticismId() {
        return criticismId;
    }

    public void setCriticismId(Long criticismId) {
        this.criticismId = criticismId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getCriticismContent() {
        return criticismContent;
    }

    public void setCriticismContent(String criticismContent) {
        this.criticismContent = criticismContent == null ? null : criticismContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}