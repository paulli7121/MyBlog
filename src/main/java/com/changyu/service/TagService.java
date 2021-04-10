package com.changyu.service;

import com.changyu.po.Tag;

import java.util.List;


public interface TagService {

    Tag getTag(Long id);

    Tag getTagByName(String name);

//    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTags();

    List<Tag> listTagsByIdList(String idList);

    List<Tag> listTagTop(Integer size);

    int saveTag(Tag tag);

    int updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
