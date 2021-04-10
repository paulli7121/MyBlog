package com.changyu.po;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Comment {

    private Long id;

    private String nickName;

    private String email;

    private String content;

    private String avatar;

    private Date createTime;

    private List<Comment> replyComments = new ArrayList<>();

    private Comment parentComment;

    private Boolean adminComment;

    // 联表查询
    private Long parentCommentId;
    private Long blogId;
}
