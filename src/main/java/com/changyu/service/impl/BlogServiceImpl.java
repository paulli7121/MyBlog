package com.changyu.service.impl;

import com.changyu.dao.BlogMapper;
import com.changyu.dao.TagMapper;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Blog;
import com.changyu.po.Tag;
import com.changyu.po.Type;
import com.changyu.service.BlogService;
import com.changyu.util.MarkdownUtils;
import com.changyu.util.MyBeanUtils;
//import com.changyu.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

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

        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));

        blogMapper.updateViewCount(id);
        return blog;
    }

    @Override
    public List<Blog> listFilteredBlogs(Blog blog) {
        return blogMapper.listFilteredBlogs(blog);
    }

    @Override
    public List<Blog> listPublishedBlogs() {
        return blogMapper.listPublishedBlogs();
    }

    @Override
    public List<Blog> listBlogsByTagId(Long tagId) {

        return blogMapper.listBlogsByTagId(tagId);
    }

    @Override
    public List<Blog> listBlogsByTypeId(Long typeId) {

        return blogMapper.listBlogsByTypeId(typeId);
    }

    @Override
    public List<Blog> listBlogs(String query) {
        return blogMapper.listQueryBlogs(query);
    }

    @Override
    public List<Blog> listPublishedRecommendBlogTop(Integer size) {
        return blogMapper.listPublishedRecommendTop(size);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for(String year : years) {
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
        blog.setViewCount(0);

        // checkbox若不选中传入null值
        if (blog.getAppreciationEnable() == null) {
            blog.setAppreciationEnable(false);
        }
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

        // checkbox若不选中传入null值
        if (blog.getAppreciationEnable() == null) {
            blog.setAppreciationEnable(false);
        }
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
        blogMapper.deleteBlogTagMappingByBlog(id);
        blogMapper.deleteById(id);
        return 1;
    }
}
