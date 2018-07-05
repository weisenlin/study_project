package com.wsl.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.wsl.study.mapper.UserMapper;
import com.wsl.study.model.User;
import com.wsl.study.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public List<User> findAll(Integer page,Integer rows,String userName) {
        PageHelper.startPage(page,rows,false);
        List<User> list = userMapper.findAllForPage(userName);
        return list;
    }

    @Override
    public int findCount() {
        return userMapper.findCount();
    }

    @Override
    public int save(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
