package com.changyu.dao;

import com.changyu.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserByNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}
