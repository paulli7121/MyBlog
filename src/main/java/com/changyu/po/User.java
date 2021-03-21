package com.changyu.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "t_user")
@Table
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String nickName;

    private String userName;

    private String password;

    private String email;

    private String avatar;

    private Integer userType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
}
