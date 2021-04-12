package com.changyu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tag {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
