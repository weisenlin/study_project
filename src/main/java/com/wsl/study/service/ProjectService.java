package com.wsl.study.service;

import com.wsl.study.model.Project;

import java.util.List;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/23 0023.
 */
public interface ProjectService {
    List<Project> selectAll();

    void tsetService();
}
