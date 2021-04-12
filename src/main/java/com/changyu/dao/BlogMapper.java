package com.changyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    Blog findById(@Param("id") Long id);

    IPage<Blog> listPublishedBlogs(Page<?> page);

    // 根据标题，分类和推荐查询博客
    IPage<Blog> listFilteredBlogs(@Param("page") Page<?> page, @Param("blog") Blog blog);

    //根据标题，内容和描述查询博客
    IPage<Blog> listQueryBlogs(Page<?> page, String query);

    List<Blog> listPublishedRecommendTop(@Param("size") Integer size);

    void updateViewCount(@Param("id") Long id);

    List<String> findGroupYear();

    List<Blog> listBlogsByYear(String year);

    IPage<Blog> listBlogsByTagId(@Param("page") Page<?> page, @Param("tagId") Long tagid);

    IPage<Blog> listBlogsByTypeId(@Param("page") Page<?> page, @Param("typeId") Long typeid);

    int saveBlog(Blog blog);

    int saveBlogTagMapping(@Param("blogId") Long blogId, @Param("tagIdList") List<Long> tagIdList);

    int updateBlog(Blog blog);

    int deleteById(@Param("id") Long id);

    int deleteBlogTagMappingByBlog(@Param("blogId") Long blogId);

    int deleteBlogTagMapping(@Param("blogId") Long blogId, @Param("tagIdList") List<Long> tagIdList);

    Long countBlogs();
}
