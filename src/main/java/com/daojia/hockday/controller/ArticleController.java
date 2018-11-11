package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.ResultDto;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     * 获取 发布的列表
     * @param type 列表类型 1:最热 2：最新
     */
    @GetMapping(value = "/get/article/list")
    public String getArticleList(Integer type){
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        /* 最热*/
        if(type == 1) {

        } else if(type == 2) {  //最近

        }

        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setArticleContent("qqq");
        articleDetail.setAuthorId(1L);
        articleDetail.setAuthorName("q");
        articleDetail.setAuthorPhoto("ww");
        articleDetail.setCreateTime(new Date());
        articleDetail.setId(1L);
        articleDetail.setCommentNum(1);
        articleDetail.setViewNum(1);
        articleDetail.setLikeNum(2);
        articleService.addArticleDetail(articleDetail);

        return JSON.toJSONString(resultDto);
    }

    /**
     * 获取 发布的列表
     * @param type 列表类型 1:最热 2：最新
     */
    @GetMapping(value = "/a")
    public String getIndex(Integer type){
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();


        return JSON.toJSONString(resultDto);
    }



}
