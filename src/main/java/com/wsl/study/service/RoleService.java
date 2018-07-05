package com.wsl.study.service;

import com.wsl.study.model.Role;

import java.util.List;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/5 0005
 * 用途：
 * 描述:
 */
public interface RoleService {

    List<Role> findRoleForUser(Long id);

    List<Role> findRole();
}
