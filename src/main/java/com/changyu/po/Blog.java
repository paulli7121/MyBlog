package com.changyu.po;

import lombok.Data;
import lombok.extern.jbosslog.JBossLog;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "t_blog")
@Table
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    private String topPicture;

    private String flag;

    private Integer viewCount;

    private Boolean appreciationEnable;

    private Boolean shareStatementEnable;

    private Boolean commentEnable;

    private Boolean published;

    private Boolean recommend;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

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
