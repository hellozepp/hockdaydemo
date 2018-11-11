package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private ArticleService articleService;


    public String getAllComment(Long articleId) {
        List<CommentLink> list = commentService.getAllComment(articleId);
        return JSON.toJSONString(list);
    }

    public String getComment(Long commentId) {
        CommentLink commentLink = commentService.getComment(commentId);
        return JSON.toJSONString(commentLink);
    }

    /**
     * @param articleId 文章id
     * @return
     * @desc 得到文章详情和评论
     **/
    public String getArticleAndComment(Long articleId) {
        JSONArray array = new JSONArray();
        List<CommentLink> list = commentService.getAllComment(articleId);
        ArticleDetail detailList = new ArticleDetail();
        JSONObject all = new JSONObject();
        all.put("artice", JSON.toJSONString(detailList));
        for (CommentLink link : list) {
            array.add(JSON.toJSONString(link));
        }
        all.put("comment", array.toJSONString());
        return all.toJSONString();
    }


}
