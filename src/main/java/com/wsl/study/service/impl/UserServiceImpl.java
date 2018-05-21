package com.wsl.study.service.impl;

import com.wsl.study.mapper.UserMapper;
import com.wsl.study.model.User;
import com.wsl.study.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/21 0021.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name,String password) {
        return userMapper.getUserByName(name,password);
    }
}
