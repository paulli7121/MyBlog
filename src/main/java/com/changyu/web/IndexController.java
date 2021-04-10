package com.changyu.web;

import com.changyu.po.Blog;
import com.changyu.service.BlogService;
import com.changyu.service.TagService;
import com.changyu.service.TypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        String orderByUpdateTime = "b.update_time desc";
        PageHelper.startPage(pageNum, 8, orderByUpdateTime);
        List<Blog> blogQueryList = blogService.listPublishedBlogs();
        PageInfo page = new PageInfo(blogQueryList);
        model.addAttribute("page", page);

        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listPublishedRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query, Model model) {
        String formatQuery = "%" + query + "%";
        String orderByUpdateTime = "b.update_time desc";
        PageHelper.startPage(pageNum, 8, orderByUpdateTime);
        List<Blog> blogQueryList = blogService.listBlogs(formatQuery);
        PageInfo page = new PageInfo(blogQueryList);
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));

        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listPublishedRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
