package com.changyu.web.admin;

import com.changyu.po.Blog;
import com.changyu.service.BlogService;
import com.changyu.service.TypeService;
import com.changyu.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    // 使用Ajax，只刷新列表部分
    @PostMapping("/blogs/search")
    public String search(Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        // 返回一个片段
        return "admin/blogs :: blogList";
    }

}
