package com.wsl.study.controller;

import com.wsl.study.model.Permission;
import com.wsl.study.model.Role;
import com.wsl.study.model.User;
import com.wsl.study.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/21 0021.
 */
public class MyRealm extends AuthorizingRealm{
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissions=new ArrayList<>();
        List<Role> roles = user.getRoles();
        if(roles.size()>0) {
            for(Role role : roles) {
                List<Permission> permissionsList = role.getPermissions();
                if(permissionsList.size()>0) {
                    for(Permission module : permissionsList) {
                        permissions.add(module.getId().toString());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }

    //认证，登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;//获取token
        String username = usernamePasswordToken.getUsername();//根据存储在token中的登录名获取用户信息
        String password = new String((char[]) authenticationToken.getCredentials());
        User user = userService.getUserByName(username,password);
        return new SimpleAuthenticationInfo(user,user.getNickname(),this.getClass().getName());
    }
}
