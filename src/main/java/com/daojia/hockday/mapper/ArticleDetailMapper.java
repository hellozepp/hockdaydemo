package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.ArticleDetail;

public interface ArticleDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleDetail record);

    int insertSelective(ArticleDetail record);


    ArticleDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleDetail record);

    int updateByPrimaryKey(ArticleDetail record);
}