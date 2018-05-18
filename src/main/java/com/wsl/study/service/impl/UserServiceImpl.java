package com.wsl.study.service.impl;

import com.wsl.study.mapper.UserMapper;
import com.wsl.study.model.User;
import com.wsl.study.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/18 0018.
 */
@Service
@Transactional
public class UserServiceImpl implements UserSerivce {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String userName, String password) {
        return userMapper.login(userName,password);
    }
}
