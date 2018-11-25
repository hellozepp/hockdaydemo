package com.daojia.hockday.service.impl;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.entity.UserInfo;
import com.daojia.hockday.mapper.ArticleDetailMapper;
import com.daojia.hockday.mapper.CommentLinkMapper;
import com.daojia.hockday.mapper.UserInfoMapper;
import com.daojia.hockday.service.CommentService;
import com.daojia.hockday.util.DateUtil;
import com.daojia.hockday.util.UniqueIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@Service
public class CommentServiceImpl implements CommentService {

    public static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Resource
    private CommentLinkMapper commentLinkMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;
    /**
     * 获取首层 用户评价
     *
     * @param articleId 文章ID
     */

    @Override
    public List<CommentLink> getDebutCommentLink(Long articleId) {
        logger.info("根据文章ID 获取评论信息， articleId={}", articleId);
        List<CommentLink> debutCommentLinkList = null;
        try {
            debutCommentLinkList = commentLinkMapper.getDebutCommentLink(articleId);
            logger.info("获取到的顶层文章评论，debutCommentLinkList={} ", JSON.toJSONString(debutCommentLinkList));
            //为评论设置用户信息
            setUserInfoWithComment(debutCommentLinkList);
            getChildComment(debutCommentLinkList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("评价列表， debutCommentLinkList= {}", JSON.toJSONString(debutCommentLinkList));
        return debutCommentLinkList;
    }


    // 递归 获取子集评论 方法
    private void getChildComment(List<CommentLink> debutCommentLinkList) {
        logger.info("获取子集评论内容");
        if (!CollectionUtils.isEmpty(debutCommentLinkList)) {
            debutCommentLinkList.forEach(debutComment -> {
                List<CommentLink> childCommentLink = commentLinkMapper.getChildCommentLink(debutComment.getId());
                setUserInfoWithComment(childCommentLink);
                debutComment.setChildCommentList(childCommentLink);
                /*if(!CollectionUtils.isEmpty(childCommentLink)) {
                    getChildComment(childCommentLink);
                }*/
            });
        }
    }

    @Override
    public Integer saveComment(CommentLink commentLink) {
        if (commentLink == null) {
            return 0;
        }
      /*  try {
            Map<String, Object> parmMap = new HashMap<>();
            parmMap.put("operationType", 2);
            parmMap.put("articleId", commentLink.getArticleId());
            Integer operationArticle = articleDetailMapper.addOperationArticle(parmMap);
            logger.info("addOperationArticle", operationArticle);
        } catch (Exception e) {
            logger.error("操作失败", e);
        }*/

        commentLink.setId(UniqueIDUtil.getUniqueID());
        commentLink.setCreateTime(new Date());
        return commentLinkMapper.insertSelective(commentLink);
    }


    @Override
    public List<CommentLink> getCommentLink(CommentLink commentLink) {
        return null;
    }


    @Override
    public List<CommentLink> getAllComment(Long articeId) {
        List<CommentLink> list = new LinkedList<>();
        if (articeId != null) {
            list = commentLinkMapper.getAllComment(articeId);
        }
        return list;
    }

    @Override
    public CommentLink getComment(Long commentId) {
        CommentLink comment = null;
        if (commentId != null) {
            comment = commentLinkMapper.selectByPrimaryKey(commentId);
        }
        return comment;
    }


    private void setUserInfoWithComment(List<CommentLink> commentLinkList) {
        logger.info("评论详情列表 去添加用户信息，commentLinkList={} ", JSON.toJSONString(commentLinkList));
        if (!CollectionUtils.isEmpty(commentLinkList)) {
            try {
                commentLinkList.forEach(commentLink -> {
                    commentLink.setCreateTimeStr(DateUtil.dateFormat(commentLink.getCreateTime()));
                    Long criticismId = commentLink.getCriticismId();
                    Long authorId = commentLink.getAuthorId();
                    UserInfo userInfo =
                            userInfoMapper.selectByPrimaryKey(criticismId);
                    logger.info("获取到的评论用户信息为，criticismId={}， userInfo={}", criticismId, JSON.toJSONString(userInfo));
                    commentLink.setCriticismUserInfo(userInfo);
                    if (authorId != null && authorId != 0) {
                        UserInfo authorUserInfo =
                                userInfoMapper.selectByPrimaryKey(authorId);
                        logger.info("获取到的作者用户信息为，authorId={}， authorUserInfo={}", authorId, JSON.toJSONString(authorUserInfo));

                        commentLink.setAuthorUserInfo(authorUserInfo);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
