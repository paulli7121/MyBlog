package com.changyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    IPage<Blog> listFilteredBlogs(Page<?> page, Blog blog);

    IPage<Blog> listPublishedBlogs(Page<?> page);

    IPage<Blog> listBlogsByTagId(Page<?> page, Long tagId);

    IPage<Blog> listBlogsByTypeId(Page<?> page, Long typeId);

    IPage<Blog> listBlogs(Page<?> page, String query);

    List<Blog> listPublishedRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

}
