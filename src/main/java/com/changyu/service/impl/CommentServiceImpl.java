package com.changyu.service.impl;

import com.changyu.dao.CommentRepository;
import com.changyu.po.Comment;
import com.changyu.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);

        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChild(commentsView);
        return commentsView;
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
        if(comment.getParentComment() != null) {
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
