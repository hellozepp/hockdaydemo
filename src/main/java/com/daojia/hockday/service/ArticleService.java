package com.daojia.hockday.service;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleOperate;
import com.daojia.hockday.entity.ArticleSearchDto;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Dawei on 2018/11/11.
 */
public interface ArticleService {


    /**
     * 添加发布文章
     * @param articleDetail 文章实体
     */
    default Integer addArticleDetail(ArticleDetail articleDetail) {return 0;}


    /**
     * 操作文章
     * @param articleOperate 文章操作实体
     */
    default Integer operationArticle(ArticleOperate articleOperate) {return 0;}

    /**
     * 获取文章列表
     * @param articleSearchDto 文章实体
     */
    default List<ArticleDetail> getArticleDetailList(ArticleSearchDto articleSearchDto) {return new ArrayList<>();}




    /**
     * 获取单个文章
     * @param articleId 文章ID
     */
    default ArticleDetail getArticleDetailById(Long articleId) {return new ArticleDetail();}









}
