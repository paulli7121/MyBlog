package com.changyu.service.impl;

import com.changyu.dao.UserMapper;
import com.changyu.po.User;
import com.changyu.service.UserService;
import com.changyu.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String userName, String password) {
        User user = userMapper.getUserByNameAndPassword(userName, MD5Utils.code(password));
        return user;
    }
}
