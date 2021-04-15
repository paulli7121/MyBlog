package com.changyu.web.admin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1", value = "data-page") Integer pageNum,
                        Blog blog, Model model) {
        Page<Blog> page = new Page<>(pageNum, 8);
        IPage<Blog> iPage = blogService.listFilteredBlogs(page, blog);
        model.addAttribute("types", typeService.listTypes());
        model.addAttribute("page", iPage);
        return LIST;
    }

    // 使用Ajax，只刷新列表部分
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         Blog blog, Model model) {
        Page<Blog> page = new Page<>(pageNum, 8);
        IPage<Blog> iPage = blogService.listFilteredBlogs(page, blog);
        model.addAttribute("page", iPage);

        // 返回一个片段
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.listTypes());
        model.addAttribute("tags", tagService.listTags());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.listTypes());
        model.addAttribute("tags", tagService.listTags());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes) {

        int res;
        if(blog.getId() == null) {
            res = blogService.saveBlog(blog);
        } else {
            res = blogService.updateBlog(blog);
        }

        if(res == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        int res = blogService.deleteBlog(id);
        if(res == 0) {
            attributes.addFlashAttribute("message", "删除失败");
        }
        else {
            attributes.addFlashAttribute("message", "删除成功");
        }
        return REDIRECT_LIST;
    }

}
