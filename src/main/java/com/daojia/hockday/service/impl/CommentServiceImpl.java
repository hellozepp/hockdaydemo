package com.daojia.hockday.service.impl;

import com.daojia.hockday.entity.CommentLink;
import com.daojia.hockday.service.CommentService;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author lei shuiyu
 * @date 2018/11/11 17:38
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<CommentLink> getAllComment(Long articeId) {
        return null;
    }

    @Override
    public CommentLink getComment(Long commentId) {
        return null;
    }

    @Override
    public void saveComment(CommentLink commentLink) {

    }
}