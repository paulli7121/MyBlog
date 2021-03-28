package com.changyu.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "t_comment")
@Table
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String nickName;

    private String email;

    private String content;

    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

    private Boolean adminComment;
}
