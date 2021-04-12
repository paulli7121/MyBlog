package com.changyu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.dao.TagMapper;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Tag;
import com.changyu.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.findById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.findByName(name);
    }

    @Override
    public IPage<Tag> listTags(Page<?> page) {
        return tagMapper.listTagsPage(page);
    }

    @Override
    public List<Tag> listTags() {
        return tagMapper.listTags();
    }

    @Override
    public List<Tag> listTagsByIdList(String idList) {
        return tagMapper.listTagsByIdList(convertStrToList(idList));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        return tagMapper.listTop(size);
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag updateTag = tagMapper.findById(id);
        if (updateTag == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, updateTag);
        return tagMapper.updateTag(updateTag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    private List<Long> convertStrToList(String str) {
        List<Long> result = new ArrayList<>();
        if ("".equals(str) || str == null) {
            return result;
        }
        String[] strList = str.split(",");
        for (String idStr : strList) {
            result.add(new Long(idStr));
        }
        return result;
    }
}
