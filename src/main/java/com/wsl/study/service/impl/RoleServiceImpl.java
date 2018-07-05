package com.wsl.study.service.impl;

import com.wsl.study.mapper.RoleMapper;
import com.wsl.study.model.Role;
import com.wsl.study.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/5 0005
 * 用途：
 * 描述:
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> findRoleForUser(Long id) {
        return roleMapper.selectRoleForUser(id);
    }

    @Override
    public List<Role> findRole() {
        return roleMapper.selectAllRole();
    }
}
