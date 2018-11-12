package com.daojia.hockday.service;

import com.daojia.hockday.entity.CommentLink;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:36
 */
public interface CommentService {


    /**
     * 获取首层 用户评价
     * @param articleId 文章ID
     */
    default List<CommentLink> getDebutCommentLink(Long articleId) {return  new ArrayList<>(); }


    /**
     * 获取首层 用户评价
     * @param commentLink 评价信息
     */
    default List<CommentLink> getCommentLink(CommentLink commentLink) {return  new ArrayList<>();}



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
