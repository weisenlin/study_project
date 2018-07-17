package com.wsl.study.dao;

import com.wsl.study.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/17 0017
 * 用途：
 * 描述:
 */
@Component
public interface UserRepository extends ElasticsearchRepository<User,Long>{
    User queryUserById(Long id);

    List<User> queryUserBynickname(String nickName);
}
