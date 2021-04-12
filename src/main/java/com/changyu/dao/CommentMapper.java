package com.changyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyu.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    Comment findById(@Param("id") long id);

    List<Comment> getCommentByBlogId();

    List<Comment> getParentCommentByBlogId(@Param("blogId") Long blogId);

    int saveComment(Comment comment);
}
