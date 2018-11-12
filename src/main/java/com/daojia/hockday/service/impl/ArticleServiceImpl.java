package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.entity.ArticleOperate;
import com.daojia.hockday.entity.ArticleSearchDto;
import com.daojia.hockday.mapper.ArticleDetailMapper;
import com.daojia.hockday.mapper.ArticleOperateMapper;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.UniqueIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author by Dawei on 2018/11/11.
 */
@Service
public class ArticleServiceImpl implements ArticleService {


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
    public List<ArticleDetail> getArticleDetailList(ArticleSearchDto articleSearchDto) {
        List<ArticleDetail> articleList = new ArrayList<>();
        if (articleSearchDto == null) {
            return articleList;
        }
        articleList = articleDetailMapper.getArticleList(articleSearchDto);
        Long authorId = articleSearchDto.getAuthorId();
        //文章已读 状态 查询
        if (!CollectionUtils.isEmpty(articleList)) {
            articleList.forEach(articleDetail ->
                articleDetail.setIfLiked(2));
            if (authorId != null) {
                try {
                    List<Long> articleIdCollect = articleList.stream().map(ArticleDetail::getId).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(articleIdCollect)) {
                        List<ArticleOperate> operationByArticleList = articleOperateMapper.getOperationByArticleIds(articleIdCollect, authorId);

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
            paraMap.put("operationType", 1);
            paraMap.put("operationValue", 1);
            paraMap.put("userId", articleOperate.getUserId());
            paraMap.put("articleId", articleOperate.getArticleId());
            if (operateType != null && operateType == 1) {
                int deleteResult = articleOperateMapper.deleteOperationByParamMap(paraMap);
                if (operateValue > 0) {
                    integer = articleDetailMapper.addOperationArticle(paraMap);
                    int insertResult = articleOperateMapper.insertSelective(articleOperate);
                } else if (operateValue < 0) {
                    integer = articleDetailMapper.subOperationArticle(paraMap);
                }
            }
        }
        return integer;
    }


    /**
     * 获取单个文章
     * @param articleId 文章ID
     */
    @Override
    public ArticleDetail getArticleDetailById(Long articleId) {
        ArticleDetail articleDetail = articleDetailMapper.selectByPrimaryKey(articleId);
        return articleDetail;
    }



}
