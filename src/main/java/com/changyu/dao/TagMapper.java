package com.changyu.dao;

import com.changyu.po.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {

    Tag findByName(@Param("name") String name);

    Tag findById(@Param("id") Long id);

    List<Tag> listTags();

    List<Tag> listTop(@Param("size") Integer size);

    List<Tag> listTagsByIdList(List<Long> tagList);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(@Param("id") Long id);
}
