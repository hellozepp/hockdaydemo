package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.mapper.ArticleDetailMapper;
import com.daojia.hockday.service.ArticleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author by Dawei on 2018/11/11.
 */
@Service
public class ArticleServiceImpl  implements ArticleService {


    @Resource
    private ArticleDetailMapper articleDetailMapper;

    @Override
    public Integer addArticleDetail(ArticleDetail articleDetail) {

        int result = articleDetailMapper.insertSelective(articleDetail);
        return result ;
    }
}
