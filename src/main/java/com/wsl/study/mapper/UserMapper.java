package com.wsl.study.mapper;

import com.wsl.study.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(String userID);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userID);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(@Param("userName") String userName, @Param("password") String password);
}