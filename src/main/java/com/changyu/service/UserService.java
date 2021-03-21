package com.changyu.service;

import com.changyu.po.User;

public interface UserService {
    User checkUser(String userName, String password);
}
