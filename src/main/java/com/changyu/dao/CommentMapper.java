package com.changyu.dao;

import com.changyu.po.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> getCommentByBlogId();

    List<Comment> getParentCommentByBlogId(Long blogId);
}
