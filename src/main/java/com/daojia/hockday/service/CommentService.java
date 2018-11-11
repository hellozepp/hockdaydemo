package com.daojia.hockday.service;

import com.daojia.hockday.entity.CommentLink;

import java.util.List;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:36
 */
public interface CommentService {

    /**
     * @param articleId 文章id
     * @return
     * @desc 得到所有的评论
     **/
    public List<CommentLink> getAllComment(Long articleId);

    /**
     * @param commentId 评价id
     * @return
     * @desc 得到评论
     **/
    public CommentLink getComment(Long commentId);


    /**
     * @desc 保存评论
     * @param
     * @return
     **/
    public void saveComment(CommentLink commentLink);
}
