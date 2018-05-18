package com.wsl;

import com.wsl.study.model.User;
import com.wsl.study.service.UserSerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyProjectApplication.class)
public class StudyProjectApplicationTests {
	@Autowired
	private UserSerivce userSerivce;
	@Test
	public void contextLoads() {
		System.out.println("nnn");
	}


	@Test
	public void testServcie(){
		User user = userSerivce.login("activi","123456");
		System.out.println(user.getUserName());
	}
}
