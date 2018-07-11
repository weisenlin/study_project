package com.wsl.study.controller;

import com.wsl.study.model.JsonResult;
import com.wsl.study.service.AysncServcie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/11 0011
 * 用途：
 * 描述:
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    private AysncServcie aysncServcie;

    @RequestMapping("/asyn.do")
    @ResponseBody
    public JsonResult testAsyn(){
        try {
            aysncServcie.runLoading();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JsonResult.ok();
    }
}
