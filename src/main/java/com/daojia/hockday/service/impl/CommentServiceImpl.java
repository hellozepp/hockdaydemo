package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.mapper.CommentLinkMapper;
import com.daojia.hockday.service.CommentService;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentLinkMapper commentLinkMapper;

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

    @Override
    public void saveComment(CommentLink commentLink) {
       commentLinkMapper.insert(commentLink);
    }
}
