package com.changyu.service;

import com.changyu.po.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    List<Blog> listFilteredBlogs(Blog blog);

    List<Blog> listPublishedBlogs();

    List<Blog> listBlogsByTagId(Long tagId);

    List<Blog> listBlogsByTypeId(Long typeId);

    List<Blog> listBlogs(String query);

    List<Blog> listPublishedRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

}
