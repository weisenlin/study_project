package com.wsl.study.controller;

import com.wsl.study.model.Role;
import com.wsl.study.model.User;
import com.wsl.study.service.RoleService;
import com.wsl.study.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/18 0018.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @RequestMapping("index.do")
    public String index() {

        return "/user/index";
    }

    @RequestMapping("/index_2.do")
    public String index_2() {

        return "/user/index_2";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer rows, String userName) {
        Map<String, Object> map = new HashMap<>();
        List<User> list = userService.findAll(page, rows, userName);
        for (User user : list) {
            List<Role> roles = roleService.findRoleForUser(user.getId());
            user.setRoles(roles);
        }
        int count = userService.findCount();
        map.put("rows", list);
        map.put("total", count);
        return map;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String, Object> save(User user) {
        Map<String, Object> map = new HashMap<>();
        userService.save(user);
        map.put("success", true);
        return map;
    }


    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String, Object> update(User user) {
        Map<String, Object> map = new HashMap<>();
        userService.update(user);
        map.put("success", true);
        return map;
    }


    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String, Object> delete(Long id) {
        Map<String, Object> map = new HashMap<>();
        userService.delete(id);
        map.put("success", true);
        return map;
    }

    @RequestMapping("/role/list.do")
    @ResponseBody
    public Map<String, Object> getRoles() {
        Map<String, Object> map = new HashMap<>();
        map.put("rows", roleService.findRole());
        return map;
    }

    @RequestMapping("/role/set.do")
    @ResponseBody
    public Map<String, Object> setRoles(Long uid,Long[] ids) {
        Map<String, Object> map = new HashMap<>();
        Long uid_1 = Long.parseLong(uid.toString());
        userService.addRole(uid_1,ids);
        map.put("success",true);
        return map;
    }

    @RequestMapping("/test.do")
    @ResponseBody
    public Map<String, Object> test(@RequestBody List<User> user) {
        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }
}
