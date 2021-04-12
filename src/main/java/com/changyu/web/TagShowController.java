package com.changyu.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Blog;
import com.changyu.po.Tag;
import com.changyu.service.BlogService;
import com.changyu.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        @PathVariable Long id,
                        Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if(id == -1) {
            id = tags.get(0).getId();
        }

        String orderBy = "b.update_time desc";
        Page<Blog> page = new Page<>(pageNum, 8);
        IPage<Blog> iPage = blogService.listBlogsByTagId(page, id);

        model.addAttribute("tags", tags);
        model.addAttribute("page", iPage);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
