package com.changyu.service.impl;

import com.changyu.dao.BlogRepository;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Blog;
import com.changyu.po.Type;
import com.changyu.service.BlogService;
import com.changyu.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Page<Blog> listBlog(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("is_recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Optional<Blog> OptionalBlog = blogRepository.findById(id);
        if(!OptionalBlog.isPresent()) {
            throw new NotFoundException("该博客不存在");
        }
        Blog updateBlog = OptionalBlog.get();
        BeanUtils.copyProperties(blog, updateBlog);
        return blogRepository.save(updateBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
