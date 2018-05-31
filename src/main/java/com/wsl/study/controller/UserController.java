package com.wsl.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/18 0018.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("index.do")
    public String index(){

        return "/user/index";
    }

    @RequestMapping("/index_2.do")
    public String index_2(){

        return "/user/index_2";
    }
}
