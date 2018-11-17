package com.daojia.hockday.entity;

import java.util.Date;
import java.util.List;

public class CommentLink {
    private Long id;

    //文章ID
    private Long articleId;
    //评论者id
    private Long criticismId;

    private Long authorId;

    private Long parentId;

    private String originalContent;

    private String criticismContent;

    private Date createTime;

    private List<CommentLink> childCommentList;

    private UserInfo criticismUserInfo;

    private UserInfo authorUserInfo;

    public CommentLink(Long id, Long articleId, Long criticismId, Long authorId, Long parentId, String originalContent, String criticismContent, Date createTime) {
        this.id = id;
        this.articleId = articleId;
        this.criticismId = criticismId;
        this.authorId = authorId;
        this.parentId = parentId;
        this.originalContent = originalContent;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent == null ? null : originalContent.trim();
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

    public List<CommentLink> getChildCommentList() {
        return childCommentList;
    }

    public void setChildCommentList(List<CommentLink> childCommentList) {
        this.childCommentList = childCommentList;
    }

    public UserInfo getCriticismUserInfo() {
        return criticismUserInfo;
    }

    public void setCriticismUserInfo(UserInfo criticismUserInfo) {
        this.criticismUserInfo = criticismUserInfo;
    }

    public UserInfo getAuthorUserInfo() {
        return authorUserInfo;
    }

    public void setAuthorUserInfo(UserInfo authorUserInfo) {
        this.authorUserInfo = authorUserInfo;
    }
}