package com.wsl.study.dto;

import com.wsl.study.model.Role;
import com.wsl.study.model.User;

import java.util.List;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/5 0005
 * 用途：
 * 描述:
 */
public class UserRoleDto {
    private List<User> user;

    private Role role;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
