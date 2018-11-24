package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.ArticleOperate;
import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.service.CommentService;
import com.daojia.hockday.util.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@RestController
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;

    @Resource
    private ArticleService articleService;

    /**
     *  保存评论
     **/
    @PostMapping(value = "/put/comment")
    public String saveComment(CommentLink commentLink) {
            logger.info("To 创建评论 commentLink={}", JSON.toJSONString(commentLink));
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setParamError();
        if(commentLink != null) {
            Integer integer = commentService.saveComment(commentLink);
            resultDto.setSuccess();
            resultDto.setData(integer);

        ArticleOperate articleOperate = new ArticleOperate();
        articleOperate.setArticleId(commentLink.getArticleId());
        articleOperate.setUserId(commentLink.getCriticismId());
        articleOperate.setOperateType(2);
        articleOperate.setOperateValue(1);
            Integer integer1 = articleService.operationArticle(articleOperate);
        }
        logger.info("Result 创建评论 commentLink={}", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }

}
