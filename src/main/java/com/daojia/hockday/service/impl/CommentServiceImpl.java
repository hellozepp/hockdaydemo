package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.mapper.CommentLinkMapper;
import com.daojia.hockday.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentLinkMapper commentLinkMapper;


    /**
     * 获取首层 用户评价
     *
     * @param articleId 文章ID
     */

    @Override
    public List<CommentLink> getDebutCommentLink(Long articleId) {
        List<CommentLink> debutCommentLinkList = null;
        try {
            debutCommentLinkList = commentLinkMapper.getDebutCommentLink(articleId);
            getChildComment(debutCommentLinkList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return debutCommentLinkList;
    }


    // 递归 获取子集评论 方法
    private void getChildComment(List<CommentLink> debutCommentLinkList) {
        if (!CollectionUtils.isEmpty(debutCommentLinkList)) {
            debutCommentLinkList.forEach(debutComment -> {
                List<CommentLink> childCommentLink = commentLinkMapper.getChildCommentLink(debutComment.getId());
                debutComment.setChildCommentList(childCommentLink);
                /*if(!CollectionUtils.isEmpty(childCommentLink)) {
                    getChildComment(childCommentLink);
                }*/
            });
        }
    }

    @Override
    public Integer saveComment(CommentLink commentLink) {
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


}
