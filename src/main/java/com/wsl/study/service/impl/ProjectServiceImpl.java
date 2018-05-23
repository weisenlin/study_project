package com.wsl.study.service.impl;

import com.wsl.study.mapper.ProjectMapper;
import com.wsl.study.model.Project;
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
    @Override
    public List<Project> selectAll() {
        return projectMapper.selectAll();
    }
}
