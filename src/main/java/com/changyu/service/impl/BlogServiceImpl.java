package com.changyu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.dao.BlogMapper;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Blog;
import com.changyu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.findById(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogMapper.findById(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }

        blogMapper.updateViewCount(id);
        return blog;
    }

    @Override
    public IPage<Blog> listFilteredBlogs(Page<?> page, Blog blog) {
        return blogMapper.listFilteredBlogs(page, blog);
    }

    @Override
    public IPage<Blog> listPublishedBlogs(Page<?> page) {
        return blogMapper.listPublishedBlogs(page);
    }

    @Override
    public IPage<Blog> listBlogsByTagId(Page<?> page, Long tagId) {

        return blogMapper.listBlogsByTagId(page, tagId);
    }

    @Override
    public IPage<Blog> listBlogsByTypeId(Page<?> page, Long typeId) {

        return blogMapper.listBlogsByTypeId(page, typeId);
    }

    @Override
    public IPage<Blog> listBlogs(Page<?> page, String query) {
        return blogMapper.listQueryBlogs(page, query);
    }

    @Override
    public List<Blog> listPublishedRecommendBlogTop(Integer size) {
        return blogMapper.listPublishedRecommendTop(size);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.listBlogsByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogMapper.countBlogs();
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setUpvote(0L);
        blog.setViewCount(0);

        // checkbox若不选中传入null值
        if (blog.getCommentEnable() == null) {
            blog.setCommentEnable(false);
        }
        if (blog.getShareStatementEnable() == null) {
            blog.setShareStatementEnable(false);
        }
        if (blog.getRecommend() == null) {
            blog.setRecommend(false);
        }

        // 保存blog
        int res = blogMapper.saveBlog(blog);

        // 保存blog-tag映射
        List<String> tagIdStrList = "".equals(blog.getTagIds()) ? new ArrayList<>() : Arrays.asList(blog.getTagIds().split(","));
        List<Long> tagIdList = new ArrayList<>();
        for (String tagId : tagIdStrList) {
            tagIdList.add(Long.valueOf(tagId));
        }

        //若tag id list为空则不必插入
        if(tagIdList.isEmpty()) {
            return 1;
        }
        return blogMapper.saveBlogTagMapping(blog.getId(), tagIdList);
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        Blog updateBlog = blogMapper.findById(blog.getId());
        if (updateBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        blog.setCreateTime(updateBlog.getCreateTime());
        blog.setUpdateTime(new Date());
        blog.setViewCount(updateBlog.getViewCount());
        blog.setUpvote(updateBlog.getUpvote());

        // checkbox若不选中传入null值
        if (blog.getCommentEnable() == null) {
            blog.setCommentEnable(false);
        }
        if (blog.getShareStatementEnable() == null) {
            blog.setShareStatementEnable(false);
        }
        if (blog.getRecommend() == null) {
            blog.setRecommend(false);
        }

        // 保存blog
        blogMapper.updateBlog(blog);

        // 保存blog-tag映射，获取需要更新的映射
        updateBlog.init(); // 将List<Tag>转换成string赋值给tagIds
        List<String> tagIdList = "".equals(blog.getTagIds()) ? new ArrayList<>() : Arrays.asList(blog.getTagIds().split(","));
        List<String> previousTagIdList = "".equals(updateBlog.getTagIds()) ? new ArrayList<>() : Arrays.asList(updateBlog.getTagIds().split(","));
        List<Long> insertIdList = new ArrayList<>();
        List<Long> deleteIdList = new ArrayList<>();
        for (String tagId : tagIdList) {
            if (!previousTagIdList.contains(tagId)) {
                insertIdList.add(Long.valueOf(tagId));
            }
        }
        for (String tagId : previousTagIdList) {
            if (!tagIdList.contains(tagId)) {
                deleteIdList.add(Long.valueOf(tagId));
            }
        }
        if (!insertIdList.isEmpty()) {
            blogMapper.saveBlogTagMapping(blog.getId(), insertIdList);
        }
        if (!deleteIdList.isEmpty()) {
            blogMapper.deleteBlogTagMapping(blog.getId(), deleteIdList);
        }
        return 1;
    }

    @Transactional
    @Override
    public int deleteBlog(Long id) {
        blogMapper.deleteById(id);
        return 1;
    }

    @Transactional
    @Override
    public int upvote(Long id) {
        return blogMapper.upvote(id);
    }
}
