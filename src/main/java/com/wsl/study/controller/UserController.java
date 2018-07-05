package com.wsl.study.controller;

import com.wsl.study.model.User;
import com.wsl.study.service.UserService;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("index.do")
    public String index(){

        return "/user/index";
    }

    @RequestMapping("/index_2.do")
    public String index_2(){

        return "/user/index_2";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String,Object> list(Integer page, Integer rows,String userName){
        Map<String,Object> map = new HashMap<>();
        List<User> list = userService.findAll(page,rows,userName);
        int count = userService.findCount();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Object> save(User user){
        Map<String,Object> map = new HashMap<>();
        userService.save(user);
        map.put("success",true);
        return map;
    }
}
