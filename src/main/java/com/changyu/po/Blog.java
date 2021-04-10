package com.changyu.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Blog {

    private Long id;

    private String title;

    private String description;

    private String content;

    private String topPicture;

    private String flag;

    private Integer viewCount;

    private Boolean appreciationEnable;

    private Boolean shareStatementEnable;

    private Boolean commentEnable;

    private Boolean published;

    private Boolean recommend;

    private Date createTime;

    private Date updateTime;

    // 联表查询
    private Long typeId;
    private Long userId;

    private String tagIds;

    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public void init() {
        this.tagIds = tagListToStr(this.getTags());
    }

    private String tagListToStr(List<Tag> tagList) {
        StringBuffer sb = new StringBuffer();
        if(tagList == null || tagList.isEmpty()) {
            return sb.toString();
        }
        for(Tag tag : tagList) {
            sb.append(tag.getId());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
