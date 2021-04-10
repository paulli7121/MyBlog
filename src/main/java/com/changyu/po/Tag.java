package com.changyu.po;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tag {

    private Long id;

    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
