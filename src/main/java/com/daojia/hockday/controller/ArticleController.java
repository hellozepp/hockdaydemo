package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.*;
import com.daojia.hockday.enums.ErrorEnum;
import com.daojia.hockday.mapper.UserInfoMapper;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.service.CommentService;
import com.daojia.hockday.util.EncryptUtil;
import com.daojia.hockday.util.ResultDto;
import com.daojia.hockday.util.UniqueIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Resource
    private ArticleService articleService;

    @Resource
    private CommentService commentService;

    @Resource
    private UserInfoMapper userInfoMapper;


    /**
     * 获取 发布的列表
     *
     * @param type 列表类型 1:最热 2：最新
     */
    @GetMapping(value = "/get/article/list")
    public String getArticleList(Integer type, Long userId, Integer pageNo) {
        logger.info("获取文章列表， type={}, userId={}", type, userId);
        if (pageNo == null || pageNo < 1) {
            pageNo = 0;
        }
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        resultDto.setCode(ErrorEnum.SUCCESS.getCode());
        resultDto.setCodeMsg(ErrorEnum.SUCCESS.getDesc());
        if (type == null) {
            resultDto.setCode(ErrorEnum.ERROR_PARAM.getCode());
            resultDto.setCodeMsg(ErrorEnum.ERROR_PARAM.getDesc());
            return JSON.toJSONString(resultDto);
        }
        /* 最热 */
        ArticleSearchDto articleSearchDto = new ArticleSearchDto();
        if (type == 2) {
            articleSearchDto.setOrderBy(" like_num desc");
        } else if (type == 1) {  //最近
            articleSearchDto.setOrderBy(" create_time desc");
        }
        articleSearchDto.setPage(pageNo * 5);
        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(articleSearchDto, userId);
        resultDto.setData(articleDetailList);
        logger.info("Result 获取文章列表值， resultDto={}", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }

    /**
     * 添加发布
     *
     * @param articleDetail 文章
     */
    @PostMapping(value = "/put/article")
    public String getIndex(ArticleDetail articleDetail, String token) {
        logger.info("To 添加文章， token={}， articleDetail={}", token, JSON.toJSONString(articleDetail));
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setSuccess();
        if (StringUtils.isNotBlank(token) && articleDetail != null) {
            UserInfo userByMd5Key = userInfoMapper.getUserByMd5Key(EncryptUtil.encrypt(token));
            if (userByMd5Key != null) {
                articleDetail.setId(UniqueIDUtil.getUniqueID());
                articleDetail.setAuthorId(userByMd5Key.getId());
                articleDetail.setAuthorName(userByMd5Key.getUserName());
                articleDetail.setAuthorPhoto(userByMd5Key.getPhotoUrl());
            }
            logger.info("添加文章内容， articleDetail{}", JSON.toJSONString(articleDetail));
            //敏感感词校验
            String articleContent = articleDetail.getArticleContent();
            if (StringUtils.isNotBlank(articleContent)) {
                if (articleContent.contains("套现")) {
                    articleDetail.setCheckNo(-1);
                } else {
                    articleDetail.setCheckNo(1);
                }
            }
            Integer integer = articleService.addArticleDetail(articleDetail);
            resultDto.setData(integer);
        } else {
            resultDto.setParamError();
        }
        logger.info("Result : 添加文章内容， resultDto={}", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }


    /**
     * 操作
     *
     * @param operationType  操作类型 ： 1:点赞
     * @param operationValue 操作值
     * @param userId         用户ID
     * @param articleId      文章ID
     */
    @PostMapping(value = "/add/operation")
    public String addOperation(Integer operationType, Integer operationValue, Long userId, Long articleId) {
        logger.info("对文章进行操作，operationType={}， operationValue={}， userId={}，articleId={} ", operationType, operationValue, userId, articleId);
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setParamError();
        if (operationType == null || operationValue == null || userId == null || articleId == null) {
            return JSON.toJSONString(resultDto);
        }
        ArticleOperate articleOperate = new ArticleOperate();
        articleOperate.setOperateType(operationType);
        articleOperate.setArticleId(articleId);
        articleOperate.setUserId(userId);
        articleOperate.setOperateValue(operationValue);
        articleOperate.setOperateTime(new Date());

        Integer integer = articleService.operationArticle(articleOperate);
        resultDto.setSuccess();
        resultDto.setData(integer);
        return JSON.toJSONString(resultDto);
    }


    /**
     * 获取文章详情
     *
     * @param articleId 文章ID
     */
    @GetMapping(value = "/get/article/detail")
    public String getArticleById(Long articleId) {
        logger.info("获取文章详情和相应的评论信息，articleId={} ", articleId);
        ResultDto<Map<String, Object>> resultDto = new ResultDto<>();
        resultDto.setParamError();
        if (articleId == null) {
            return JSON.toJSONString(resultDto);
        }
        resultDto.setSuccess();
        ArticleDetail articleDetailById = articleService.getArticleDetailById(articleId);
        logger.info("文章结果， articleDetailById={}", JSON.toJSONString(articleDetailById));
        List<CommentLink> debutCommentLink = commentService.getDebutCommentLink(articleId);
        logger.info("评论内容， debutCommentLink={}", JSON.toJSONString(debutCommentLink));
        Map<String, Object> resultMap = new HashMap<>();
        resultDto.setData(resultMap);
        resultMap.put("articleDetail", articleDetailById);
        resultMap.put("debutCommentLink", debutCommentLink);
        logger.info("查询结果， resultDto={}", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }


    /**
     * 获取 我的发布的列表
     */
    @GetMapping(value = "/get/my/article/list")
    public String getMyArticleList(Long userId) {
        logger.info("获取文章列表 userId={}", userId);

        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        if (userId == null) {
            resultDto.setParamError();
        } else {
            resultDto.setCode(ErrorEnum.SUCCESS.getCode());
            resultDto.setCodeMsg(ErrorEnum.SUCCESS.getDesc());
            ArticleSearchDto searchDto = new ArticleSearchDto();
            searchDto.setAuthorId(userId);
            List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(searchDto, userId);
            resultDto.setData(articleDetailList);
        }
        logger.info("Result 获取我的发布 文章列表值， resultDto={}", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }
}
























