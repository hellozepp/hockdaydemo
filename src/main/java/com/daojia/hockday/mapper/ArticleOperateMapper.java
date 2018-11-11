package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.ArticleOperate;

public interface ArticleOperateMapper {


    int deleteByPrimaryKey(Long id);

    int insert(ArticleOperate record);

    int insertSelective(ArticleOperate record);


    ArticleOperate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleOperate record);

    int updateByPrimaryKey(ArticleOperate record);
}