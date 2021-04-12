package com.changyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    Tag findByName(@Param("name") String name);

    Tag findById(@Param("id") Long id);

    List<Tag> listTags();

    IPage<Tag> listTagsPage(Page<?> page);

    List<Tag> listTop(@Param("size") Integer size);

    List<Tag> listTagsByIdList(List<Long> tagList);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(@Param("id") Long id);
}
