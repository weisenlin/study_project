package com.wsl.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/18 0018.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping("")
    public String toLogin(){

        return "login";
    }
}
