package com.wsl.study.controller;

import com.wsl.study.model.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping("layout.do")
    public String layout(Model model){
//        model.addAttribute("user","user");
        return "layout";
    }

    @RequestMapping("ajaxLogin.do")
    @ResponseBody
    public JsonResult login(String username,String password){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                username,
                password);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return JsonResult.build(200,"ok",null);
    }
}
