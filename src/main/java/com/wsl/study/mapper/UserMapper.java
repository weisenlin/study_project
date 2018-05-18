package com.wsl.study.mapper;

import com.wsl.study.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userID);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userID);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}