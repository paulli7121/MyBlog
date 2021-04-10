package com.changyu.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class Type {

    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
