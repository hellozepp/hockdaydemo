package com.daojia.hockday.service;

import com.daojia.hockday.entity.ArticleDetail;

/**
 * @author by Dawei on 2018/11/11.
 */
public interface ArticleService {



    default Integer addArticleDetail(ArticleDetail articleDetail) {return 0;}
}
