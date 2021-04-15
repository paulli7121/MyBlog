package com.changyu.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Blog;
import com.changyu.service.BlogService;
import com.changyu.service.TagService;
import com.changyu.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {

        Page<Blog> page = new Page<>(pageNum, 8);
        IPage<Blog> iPage = blogService.listPublishedBlogs(page);
        model.addAttribute("page", iPage);

        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listPublishedRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query, Model model) {
        String formatQuery = "%" + query + "%";
        Page<Blog> page = new Page<>(pageNum, 8);
        IPage<Blog> iPage = blogService.listBlogs(page, formatQuery);
        model.addAttribute("page", iPage);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));

        return "blog";
    }

    @GetMapping("/blogs/{id}/upvote")
    public String upvote(@PathVariable Long id, Model model) {
        blogService.upvote(id);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "blog :: upvote";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listPublishedRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
