package com.changyu.service.impl;

import com.changyu.dao.CommentMapper;
import com.changyu.po.Comment;
import com.changyu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.getParentCommentByBlogId(blogId);
        combineChild(comments);

        // 为子评论设置父评论
        for(Comment comment : comments) {
            for(Comment childComment : comment.getReplyComments()) {
                Comment parentComment = commentMapper.findById(childComment.getParentCommentId());
                childComment.setParentComment(parentComment);
//                childComment.setParentCommentId(comment.getId());
            }
        }
        return comments;
    }

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentMapper.findById(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    private void combineChild(List<Comment> comments) {
        for(Comment comment : comments) {
            List<Comment> childList = new ArrayList<>();
            findChild(comment, childList);
            comment.setReplyComments(childList);
        }
    }

    private void findChild(Comment comment, List<Comment> childList) {
        // 顶层节点无需放入
        if(comment.getParentCommentId() != -1) {
            childList.add(comment);
        }
        if(comment.getReplyComments().isEmpty()) {
            return;
        }
        for(Comment c : comment.getReplyComments()) {
            findChild(c, childList);
        }
        return;
    }
}
