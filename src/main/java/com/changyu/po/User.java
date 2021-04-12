package com.changyu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {

    @TableId(type= IdType.AUTO)
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
