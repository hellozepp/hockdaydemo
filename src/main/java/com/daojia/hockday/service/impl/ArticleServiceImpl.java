package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleSearchDto;
import com.daojia.hockday.mapper.ArticleDetailMapper;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.UniqueIDUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author by Dawei on 2018/11/11.
 */
@Service
public class ArticleServiceImpl  implements ArticleService {


    @Resource
    private ArticleDetailMapper articleDetailMapper;

    /**
     * 添加文章
     * @param articleDetail 文章实体
     */
    @Override
    public Integer addArticleDetail(ArticleDetail articleDetail) {
        int result = 0;
        if (articleDetail != null) {
            if (articleDetail.getId() == null) {
                articleDetail.setId(UniqueIDUtil.getUniqueID());
            }
            articleDetail.setCreateTime(new Date());
            result =  articleDetailMapper.insertSelective(articleDetail);
        }
        return result ;
    }

    /**
     * 获取文章列表
     * @param articleSearchDto 参数
     */
    @Override
    public List<ArticleDetail> getArticleDetailList(ArticleSearchDto articleSearchDto) {
        List<ArticleDetail> articleList = null;
        if(articleSearchDto != null) {
            articleList = articleDetailMapper.getArticleList(articleSearchDto);
        }
        return articleList;
    }

    /**
     * 用户操作
     * @param paraMap 操作
     */
    @Override
    public Integer operationArticle(Map<String, Object> paraMap) {
        Integer integer = 0;
        if(paraMap != null) {
            integer = articleDetailMapper.operationArticle(paraMap);
        }
        return integer;
    }
}
