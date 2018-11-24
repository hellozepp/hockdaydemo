package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.CommentLink;

import java.util.List;

public interface CommentLinkMapper {



    /* 获取 首级评价*/
    List<CommentLink> getDebutCommentLink(Long articleId);

    /* 根据 父级评价获取 子评价 */
    List<CommentLink> getChildCommentLink(Long parentId);

    int deleteByPrimaryKey(Long id);

    int insert(CommentLink record);

    int insertSelective(CommentLink record);

    CommentLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentLink record);

    int updateByPrimaryKey(CommentLink record);

    List<CommentLink> getAllComment(Long articleId);

}