package com.changyu.web;

import com.changyu.po.Blog;
import com.changyu.po.Type;
import com.changyu.service.BlogService;
import com.changyu.service.TypeService;
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
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        @PathVariable Long id,
                        Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if(id == -1) {
            id = types.get(0).getId();
        }

        String orderBy = "b.update_time desc";
        PageHelper.startPage(pageNum, 8, orderBy);
        List<Blog> blogQueryList = blogService.listBlogsByTypeId(id);
        PageInfo page = new PageInfo(blogQueryList);

        model.addAttribute("types", types);
        model.addAttribute("page", page);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
