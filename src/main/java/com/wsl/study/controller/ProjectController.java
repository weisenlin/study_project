package com.wsl.study.controller;

import com.wsl.study.model.Project;
import com.wsl.study.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 名称:
 * 用途：
 * Created by SILVA_WSL on 2018/5/23 0023.
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("index.do")
    public String index(){

        return "/project/index";
    }

    @RequestMapping("list.do")
    @ResponseBody
    public List<Project> list(){

        return projectService.selectAll();
    }

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        projectService.tsetService();
        return "";
    }
}
