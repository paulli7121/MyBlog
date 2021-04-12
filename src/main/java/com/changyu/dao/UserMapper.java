package com.changyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyu.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}
