package com.changyu.web.admin;

import com.changyu.po.Tag;
import com.changyu.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue="1", value = "pageNum") Integer pageNum , Model model) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, 5, orderBy);
        List<Tag> tagList = tagService.listTags();
        PageInfo page = new PageInfo(tagList);
        model.addAttribute("page", page);
        return "admin/tags";
    }

    @GetMapping("/tags/insert")
    public String insert(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-insert";
    }

    @GetMapping("/tags/{id}/insert")
    public String editInsert(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-insert";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        if(tagService.getTagByName(tag.getName()) != null) {
            result.rejectValue("name", "nameError", "不能添加重复标签");
        }
        if(result.hasErrors()) {
            return "admin/tags-insert";
        }

        int res = tagService.saveTag(tag);
        if(res == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        if(tagService.getTagByName(tag.getName()) != null) {
            result.rejectValue("name", "nameError", "不能添加重复标签");
        }
        if(result.hasErrors()) {
            return "admin/tags-insert";
        }

        int res = tagService.updateTag(id, tag);
        if(res == 0) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
