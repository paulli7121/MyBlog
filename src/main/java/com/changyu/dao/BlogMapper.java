package com.changyu.dao;

import com.changyu.po.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    Blog findById(@Param("id") Long id);

    List<Blog> listPublishedBlogs();

    // 根据标题，分类和推荐查询博客
    List<Blog> listFilteredBlogs(Blog blog);

    //根据标题，内容和描述查询博客
    List<Blog> listQueryBlogs(String query);

    List<Blog> listPublishedRecommendTop(@Param("size") Integer size);

    void updateViewCount(@Param("id") Long id);

    List<String> findGroupYear();

    List<Blog> listBlogsByYear(String year);

    List<Blog> listBlogsByTagId(@Param("tagId") Long tagid);

    List<Blog> listBlogsByTypeId(@Param("typeId") Long typeid);

    int saveBlog(Blog blog);

    int saveBlogTagMapping(@Param("blogId") Long blogId, @Param("tagIdList") List<Long> tagIdList);

    int updateBlog(Blog blog);

    int deleteById(@Param("id") Long id);

    int deleteBlogTagMappingByBlog(@Param("blogId") Long blogId);

    int deleteBlogTagMapping(@Param("blogId") Long blogId, @Param("tagIdList") List<Long> tagIdList);

    Long countBlogs();
}
