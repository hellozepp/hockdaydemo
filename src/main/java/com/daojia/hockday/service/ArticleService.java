package com.daojia.hockday.service;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleSearchDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @param paraMap 文章实体
     */
    default Integer operationArticle(Map<String, Object> paraMap) {return 0;}

    /**
     * 操作文章
     * @param articleSearchDto 文章实体
     */
    default List<ArticleDetail> getArticleDetailList(ArticleSearchDto articleSearchDto) {return new ArrayList<>();}










}
