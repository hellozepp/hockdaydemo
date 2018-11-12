package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.CommentLink;

import java.util.List;

public interface CommentLinkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CommentLink record);

    int insertSelective(CommentLink record);

    CommentLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentLink record);

    int updateByPrimaryKey(CommentLink record);

    List<CommentLink> getAllComment(Long articelId);
}