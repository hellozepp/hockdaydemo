package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleSearchDto;
import java.util.List;
import java.util.Map;

public interface ArticleDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleDetail record);

    int insertSelective(ArticleDetail record);

    ArticleDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleDetail record);

    int updateByPrimaryKey(ArticleDetail record);

    /* 查询文章列表 */
    List<ArticleDetail> getArticleList(ArticleSearchDto articleSearchDto);

    /* 添加对文章的操作 */
    Integer addOperationArticle(Long articleId);

    Integer addCommentNum(Long articleId);

    /* 减少  */
    Integer subOperationArticle(Long articleId);
}