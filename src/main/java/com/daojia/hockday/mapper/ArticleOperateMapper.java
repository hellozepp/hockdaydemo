package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.ArticleOperate;
import java.util.List;
import java.util.Map;

public interface ArticleOperateMapper {


    int deleteByPrimaryKey(Long id);

    int insert(ArticleOperate record);

    int insertSelective(ArticleOperate record);


    ArticleOperate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleOperate record);

    int updateByPrimaryKey(ArticleOperate record);

    List<ArticleOperate> getOperationByArticleIds(List<Long> articleIdCollect, Long userId);


    int deleteOperationByParamMap(Map<String, Object> paramMap);

}

