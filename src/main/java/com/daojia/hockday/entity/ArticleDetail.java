package com.daojia.hockday.entity;

import java.io.Serializable;
import java.util.Date;

public class ArticleDetail implements Serializable {

    private static final long serialVersionUID = -6443558670738567149L;

    /** 文章id */
    private Long id;

    /** 发布者ID */
    private Long authorId;

    /** 发布者操作人 */
    private String authorName;

    /**  作者头像*/
    private String authorPhoto;

    /** 文章标题 */
    private String title;

    /** 正文 */
    private String articleContent;

    /** 浏览量 */
    private Integer viewNum;

    /** 点赞数 */
    private Integer likeNum;

    private Integer ifLiked;

    /** 评论数 */
    private Integer commentNum;

    /** 发布时间  */
    private Date createTime;

    public ArticleDetail(Long id, Long authorId, String authorName, String authorPhoto, String title, String articleContent, Integer viewNum, Integer likeNum, Integer commentNum, Date createTime) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
        this.title = title;
        this.articleContent = articleContent;
        this.viewNum = viewNum;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.createTime = createTime;
    }

    public ArticleDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public void setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto == null ? null : authorPhoto.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent == null ? null : articleContent.trim();
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getIfLiked() {
        return ifLiked;
    }

    public void setIfLiked(Integer ifLiked) {
        this.ifLiked = ifLiked;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}