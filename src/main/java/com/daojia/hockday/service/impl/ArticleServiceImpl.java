package com.daojia.hockday.service.impl;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleOperate;
import com.daojia.hockday.entity.ArticleSearchDto;
import com.daojia.hockday.mapper.ArticleDetailMapper;
import com.daojia.hockday.mapper.ArticleOperateMapper;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.DateUtil;
import com.daojia.hockday.util.UniqueIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author by Dawei on 2018/11/11.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    public static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    //特权用户列表
    private List<Long> privilegeUserList = new ArrayList<>();

    {
        privilegeUserList.add(124L);
    }

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    @Resource
    private ArticleOperateMapper articleOperateMapper;

    /**
     * 添加文章
     *
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
            result = articleDetailMapper.insertSelective(articleDetail);
        }
        return result;
    }

    /**
     * 获取文章列表
     *
     * @param articleSearchDto 参数
     */
    @Override
    public List<ArticleDetail> getArticleDetailList(ArticleSearchDto articleSearchDto, Long userId) {
        List<ArticleDetail> articleListTemp = new ArrayList<>();
        if (articleSearchDto == null) {
            return articleListTemp;
        }
        logger.info("To 获取文章列表， articleSearchDto={}， userId={}", JSON.toJSONString(articleSearchDto), userId);
        articleListTemp = articleDetailMapper.getArticleList(articleSearchDto);
        List<ArticleDetail> articleList = new ArrayList<>();
        //踢掉不可见
        if (!CollectionUtils.isEmpty(articleListTemp)) {
            for (ArticleDetail articleDetail : articleListTemp) {
                boolean canSeeIt = true;
                //不可见文章
                if (articleDetail.getCheckNo() != null && articleDetail.getCheckNo() == -1) {
                    ///未登录用户 不可见 用户 不是发帖人 不可见
                    if (userId == null || !userId.equals(articleDetail.getAuthorId())) {
                        canSeeIt = false;
                    }
                }
                if (canSeeIt) {
                    articleList.add(articleDetail);
                }
            }
        }

        if (!CollectionUtils.isEmpty(articleList)) {
            articleList.forEach(articleDetail -> {
                articleDetail.setIfLiked(2);
                articleDetail.setCreateTimeStr(DateUtil.dateFormat(articleDetail.getCreateTime()));
            });
            if (userId != null) {
                try {
                    List<Long> articleIdCollect = articleList.stream().map(ArticleDetail::getId).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(articleIdCollect)) {
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("userId", userId);
                        paramMap.put("operationType", 1);
                        paramMap.put("articleIdList", articleIdCollect);
                        //点赞状态 查询
                        List<ArticleOperate> operationByArticleList = articleOperateMapper.selectOperationByArticles(paramMap);

                        List<Long> readIdList = operationByArticleList.stream().map(ArticleOperate::getArticleId).collect(Collectors.toList());
                        articleList.forEach(articleConfigResultDto -> {
                            if (readIdList.contains(articleConfigResultDto.getId())) {
                                articleConfigResultDto.setIfLiked(1);
                            }
                        });
                    }
                } catch (Exception e) {
                    //logger.error(" Check whether read failed, e=", e);
                    e.printStackTrace();
                }
            }
        }
        logger.info("文章列表，articleList={}", JSON.toJSONString(articleList));
        return articleList;
    }

    /**
     * 用户操作
     *
     * @param articleOperate 操作
     */
    @Override
    public Integer operationArticle(ArticleOperate articleOperate) {
        Integer integer = 0;
        if (articleOperate != null) {
            Integer operateValue = articleOperate.getOperateValue();
            Integer operateType = articleOperate.getOperateType();

            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("operationType", operateType);
            paraMap.put("userId", articleOperate.getUserId());
            paraMap.put("articleId", articleOperate.getArticleId());
            if (operateType != null && operateValue != null) {
                int deleteResult = articleOperateMapper.deleteOperationByParamMap(paraMap);
                if (operateType == 1) {
                    if (operateValue == 1) {
                        integer = articleDetailMapper.addOperationArticle(articleOperate.getArticleId());
                    } else if (operateValue == 2) {
                        Integer integer1 = articleDetailMapper.subOperationArticle(articleOperate.getArticleId());
                    }
                    articleOperate.setId(UniqueIDUtil.getUniqueID());
                    articleOperate.setOperateTime(new Date());
                    int insertResult = articleOperateMapper.insertSelective(articleOperate);
                } else if (operateType == 2) {
                    integer = articleDetailMapper.addCommentNum(articleOperate.getArticleId());
                }
            }
        }
        return integer;
    }


    /**
     * 获取单个文章
     *
     * @param articleId 文章ID
     */
    @Override
    public ArticleDetail getArticleDetailById(Long articleId) {
        ArticleDetail articleDetail = articleDetailMapper.selectByPrimaryKey(articleId);
        articleDetail.setCreateTimeStr(DateUtil.dateFormat(articleDetail.getCreateTime()));


        return articleDetail;
    }


    @Override
    public void updateState(Long id) {
        articleDetailMapper.updateState(id);
    }

    @Override
    public List<ArticleDetail> getAllTicle() {
        List<ArticleDetail> list = articleDetailMapper.getAll();
        return list;
    }


}
