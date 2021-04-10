package com.changyu.web;

import com.changyu.po.Blog;
import com.changyu.po.Tag;
import com.changyu.service.BlogService;
import com.changyu.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        PageHelper.startPage(pageNum, 8, orderBy);
        List<Blog> blogQueryList = blogService.listBlogsByTagId(id);
        PageInfo page = new PageInfo(blogQueryList);

        model.addAttribute("tags", tags);
        model.addAttribute("page", page);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
