package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.service.CommentService;
import com.daojia.hockday.util.ResultDto;
import org.springframework.web.bind.annotation.*;

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



    @RequestMapping(value = "/get/allcomment", method = RequestMethod.GET)
    public String getAllComment(String articleId) {
        List<CommentLink> list = commentService.getAllComment(Long.valueOf(articleId));
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/get/comment", method = RequestMethod.GET)
    public String getComment(String commentId) {
        CommentLink commentLink = commentService.getComment(Long.valueOf(commentId));
        return JSON.toJSONString(commentLink);
    }

    /**
     * @param articleId 文章id
     * @return
     * @desc 得到文章详情和评论
     **/
    @RequestMapping(value = "/comment/article", method = RequestMethod.GET)
    public String getArticleAndComment(Long articleId) {
        JSONArray array = new JSONArray();
        List<CommentLink> list = commentService.getAllComment(articleId);
        ArticleDetail detailList = articleService.getArticleDetailById(articleId);
        JSONObject all = new JSONObject();
        all.put("artice", JSON.toJSONString(detailList));
        for (CommentLink link : list) {
            array.add(JSON.toJSONString(link));
        }
        all.put("comments", array.toJSONString());
        return all.toJSONString();
    }

    /**
     * @desc 保存评论
     **/
    @PutMapping(value = "/put/comment")
    public String saveComment(CommentLink commentLink) {
        commentService.saveComment(commentLink);
        return JSON.toJSONString(new ResultDto<>(1));
    }


}
