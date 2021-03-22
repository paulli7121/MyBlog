package com.changyu.po;

import lombok.Data;

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

    private String content;

    private String topPicture;

    private String flag;

    private Integer viewCount;

    private Boolean is_appreciationEnable;

    private Boolean is_shareStatementEnable;

    private Boolean is_commentEnable;

    private Boolean is_published;

    private Boolean is_recommend;

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

}
