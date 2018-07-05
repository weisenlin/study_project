package com.wsl.study.service;

import com.wsl.study.model.User;

import java.util.List;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/21 0021.
 */
public interface UserService {

    User getUserByName(String name);

    List<User> findAll(Integer page,Integer rows);

    int findCount();
}
