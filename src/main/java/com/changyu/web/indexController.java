package com.changyu.web;

import com.changyu.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class indexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/types")
    public String types() {
        return "types";
    }

    @GetMapping("/tags")
    public String tags() {
        return "tags";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/archives")
    public String archives() {
        return "archives";
    }

    @GetMapping("/admin/blogs")
    public String adminBlogs() {
        return "/admin/blogs";
    }

    @GetMapping("/admin/blogs-input")
    public String adminBlogsInput() {
        return "/admin/blogs-input";
    }
}
