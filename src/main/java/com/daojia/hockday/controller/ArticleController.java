package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleOperate;
import com.daojia.hockday.entity.ArticleSearchDto;
import com.daojia.hockday.enums.ErrorEnum;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.ResultDto;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     * 获取 发布的列表
     *
     * @param type 列表类型 1:最热 2：最新
     */
    @GetMapping(value = "/get/article/list")
    public String getArticleList(Integer type) {
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
        if (type == 1) {
            articleSearchDto.setOrderBy(" like_num desc");
        } else if (type == 2) {  //最近
            articleSearchDto.setOrderBy(" create_time desc");
        }
        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(articleSearchDto);
        resultDto.setData(articleDetailList);
        return JSON.toJSONString(resultDto);
    }

    /**
     * 添加发布
     *
     * @param articleDetail 文章
     */
    @PutMapping(value = "/put/article")
    public String getIndex(ArticleDetail articleDetail) {
        Integer integer = articleService.addArticleDetail(articleDetail);
        return JSON.toJSONString(integer);
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
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ErrorEnum.ERROR_PARAM.getCode());
        resultDto.setCodeMsg(ErrorEnum.ERROR_PARAM.getDesc());
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
        return JSON.toJSONString(integer);
    }

}
