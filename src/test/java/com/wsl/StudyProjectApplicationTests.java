package com.wsl;

import com.wsl.study.mapper.SurveyEntityMapper;
import com.wsl.study.model.SurveyEntity;
import com.wsl.study.service.MailService;
import com.wsl.study.service.ProjectService;
import com.wsl.study.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyProjectApplication.class)
public class StudyProjectApplicationTests {
	@Autowired
	private UserService userSerivce;
	@Autowired
	private ProjectService projectService;

	@Autowired
	private SurveyEntityMapper surveyEntityMapper;

	@Autowired
	private MailService mailService;

	private String to = "3345.love@163.com";

	@Test
	public void contextLoads() {
		System.out.println("nnn");
	}


	@Test
	public void testServcie(){
//		User user = userSerivce.login("activi","123456");
//		System.out.println(user.getUserName());
		Integer user = userSerivce.findCount();
//		projectService.tsetService();
	}

	@Test
	public void testInsert(){
		SurveyEntity surveyEntity = new SurveyEntity();
		surveyEntity.setTitle("测试");
		surveyEntityMapper.insertSelective(surveyEntity);
	}

	@Test
	public void testMail(){
		mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");
	}
}
