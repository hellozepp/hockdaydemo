package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daojia.hockday.entity.*;
import com.daojia.hockday.enums.ErrorEnum;
import com.daojia.hockday.mapper.UserInfoMapper;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.service.CommentService;
import com.daojia.hockday.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * @param type 列表类型 1:最新 2：最热
     */
    @GetMapping(value = "/get/article/list")
    public String getArticleList(Integer type, Long userId, Integer pageNo, Integer pageSize) {
        logger.info("获取文章列表， type={}, userId={}", type, userId);
        logger.error("获取文章列表， type={}, userId={}", type, userId);
        if (pageNo == null || pageNo < 1) {
            pageNo = 0;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        resultDto.setCode(ErrorEnum.SUCCESS.getCode());
        resultDto.setCodeMsg(ErrorEnum.SUCCESS.getDesc());
        if (type == null) {
            type = 1;
        }

        ArticleSearchDto articleSearchDto = new ArticleSearchDto();
        if (type.equals(1)) {  //最新
            articleSearchDto.setOrderBy(" create_time desc");
        } else if (type.equals(2)) { /* 最热 */
            articleSearchDto.setOrderBy(" like_num desc");
        }
        articleSearchDto.setPage(pageNo * pageSize);
        articleSearchDto.setPageSize(pageSize);

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
            String tokenNo = MD5Util.userToken(token);
            UserInfo userByMd5Key = userInfoMapper.getUserByMd5Key(tokenNo);
            if (userByMd5Key != null) {
                articleDetail.setId(UniqueIDUtil.getUniqueID());
                articleDetail.setAuthorId(userByMd5Key.getId());
                articleDetail.setAuthorName(userByMd5Key.getUserName());
                articleDetail.setAuthorPhoto(userByMd5Key.getPhotoUrl());
            }
            logger.info("添加文章内容， articleDetail{}", JSON.toJSONString(articleDetail));
            //敏感感词校验
            String articleContent = articleDetail.getArticleContent();
            articleDetail.setCheckNo(2);
            articleDetail.setCreateTime(new Date());


            /*String urlPath = "http://dmatrix-218.djtest.cn/admin/groupContent";
            //String urlPath = "http://127.0.0.1:8080/check";
            CloseableHttpClient httpClient = HttpClients.createDefault();

            List<NameValuePair> nameValuePairList = new ArrayList<>();
            BasicNameValuePair basicNameValuePair1 = new BasicNameValuePair("articleContent", articleContent);
            nameValuePairList.add(basicNameValuePair1);
            try {
                UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairList, Consts.UTF_8);
                String params = EntityUtils.toString(encodedFormEntity);
                HttpGet httpGet = new HttpGet(urlPath + "?" + params);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "utf-8");
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>.Response is content= {}", content);
                if(StringUtils.isNotBlank(content)) {
                    if("2".equals(content)) {
                        articleDetail.setCheckNo(-1);
                    } else {
                        articleDetail.setCheckNo(1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            Set<String> set1 = new HashSet<>();
            set1.add("贩毒");
            set1.add("套现");
            for (String s : set1) {
                if (articleContent.contains(s)) {
                    articleDetail.setCheckNo(-1);
                }
            }
            Set<String> set2 = new HashSet<>();
            set2.add("美立方");
            for (String s : set2) {
                if (articleContent.contains(s)) {
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
// TODO: 2018/11/25 张琳
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
    public String getArticleById(Long articleId, Long userId) {
        logger.info("获取文章详情和相应的评论信息，articleId={} userId={}", articleId, userId);
        ResultDto<Map<String, Object>> resultDto = new ResultDto<>();
        resultDto.setParamError();
        if (articleId == null) {
            return JSON.toJSONString(resultDto);
        }
        resultDto.setSuccess();

        ArticleDetail articleDetailById = null;
        ArticleSearchDto articleSearchDto = new ArticleSearchDto();
        articleSearchDto.setId(articleId);
        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(articleSearchDto, userId);
        if (!CollectionUtils.isEmpty(articleDetailList)) {
            articleDetailById = articleDetailList.get(0);
        }
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


    /**
     * 得到评论
     **/
    @PostMapping(value = "/get/comment")
    public String getComment(HttpServletResponse response) {

        List<ArticleDetail> allComment = articleService.getAllTicle();
        List<ShowContent> va = new LinkedList<>();
        PageList<ShowContent> pageList = new PageList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        for (ArticleDetail article : allComment) {
            ShowContent content = new ShowContent();
            content.setId(article.getId());
            content.setContent(article.getArticleContent());
            content.setLevel(state(article.getCheckNo()));
            content.setState(conver(article.getCheckNo()));
            content.setPublicTime(formatter.format(article.getCreateTime()));
            content.setLike(article.getLikeNum());
            content.setComments(article.getCommentNum());
            content.setEmotional("#" + RequestUtil.getsentiment(article.getArticleContent()));
            va.add(content);
        }
        pageList.setList(va);
        pageList.setAmount(100);
        pageList.setPage(1);
        pageList.setPageSize(10);
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "success");
        object.put("result", JSON.toJSONString(pageList));
        return object.toString();
    }


    public String conver(Integer check) {
        if (check!=null){
            if (check == -1) {
                return "不通过";
            } else if (check == 1) {
                return "待审核";
            } else {
                return "通过";
            }
        }else {
            return "不通过";
        }
    }


    public String state(Integer check) {
        if (check!=null){
            if (check == -1) {
                return "一级";
            } else if (check == 1) {
                return "二级";
            } else {
                return "三级";
            }
        }else {
            return "一级";
        }

    }


    /**
     * 更新成功状态
     **/
    @PostMapping(value = "/update/pass")
    public String update(Long articleId) {
        articleService.updatePassState(articleId);
        System.out.println(articleId);
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "success");
        object.put("result", "");
        return object.toString();
    }


    /**
     * 更新未通过状态
     **/
    @PostMapping(value = "/update/nopass")
    public String updateNoPass(Long articleId) {
        articleService.updateNoPass(articleId);
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "success");
        object.put("result", "");
        return object.toString();
    }


}
