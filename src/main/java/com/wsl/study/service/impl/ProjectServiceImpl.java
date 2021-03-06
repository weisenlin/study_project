package com.wsl.study.service.impl;

import com.wsl.study.mapper.ProjectMapper;
import com.wsl.study.mapper.UserMapper;
import com.wsl.study.model.Project;
import com.wsl.study.model.User;
import com.wsl.study.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/23 0023.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Project> selectAll() {
        return projectMapper.selectAll();
    }

    @Override
    public void tsetService() {
//        测试事务
        Project project = new Project();
        project.setName("test");
        projectMapper.insert(project);
        User user = new User();
        user.setNickname("nick");
        userMapper.insert(user);
//        int i = 1/0;
    }
}
