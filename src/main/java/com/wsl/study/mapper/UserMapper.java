package com.wsl.study.mapper;

import com.wsl.study.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserByName(@Param("nickname")String name);

    List<User> findAll(@Param("userName")String userName);

    int findCount();
}