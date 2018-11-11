package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.CommentLink;

public interface CommentLinkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CommentLink record);

    int insertSelective(CommentLink record);

    CommentLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentLink record);

    int updateByPrimaryKey(CommentLink record);
}