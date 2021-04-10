package com.changyu.po;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {

    private long id;

    private String nickName;

    private String userName;

    private String password;

    private String email;

    private String avatar;

    private Integer userType;

    private Date createTime;

    private Date updateTime;

    private List<Blog> blogs = new ArrayList<>();
}
