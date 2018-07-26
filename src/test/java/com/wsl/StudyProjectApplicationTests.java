package com.wsl;

import com.wsl.study.constant.QueueConstant;
import com.wsl.study.mapper.SurveyEntityMapper;
import com.wsl.study.message.provider.Producer;
import com.wsl.study.model.SurveyEntity;
import com.wsl.study.model.User;
import com.wsl.study.service.MailService;
import com.wsl.study.service.ProjectService;
import com.wsl.study.service.UserService;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;

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

    @Autowired
    private RedisTemplate redisTemplate;

    private String to = "3345.love@163.com";

    @Autowired
    private Producer producer;

    @Test
    public void contextLoads() {
        System.out.println("nnn");
    }


    @Test
    public void testServcie() {
//		User user = userSerivce.login("activi","123456");
//		System.out.println(user.getUserName());
        Integer user = userSerivce.findCount();
//		projectService.tsetService();
    }

    @Test
    public void testInsert() {
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setTitle("测试");
        surveyEntityMapper.insertSelective(surveyEntity);
    }

    @Test
    public void testMail() {
        mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");
    }


    @Test
    public void testRedis() {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
//		hashOperations.put("test","test_1","测试信息");
//
//		System.out.println(hashOperations.get("test","test_1"));

        ValueOperations valueOperations = redisTemplate.opsForValue();
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("test_push", "测试push");
//		System.out.println(listOperations.rightPop("test_push"));
//		System.out.println(listOperations.rightPop("test_push"));
    }

    @Test
    public void testEs() {
        userSerivce.testEs();
    }

    @Test
    public void testEsSearch() {
        User user = userSerivce.searchFromEs();
    }


    @Test
    public void sendMessage() {
        //通过topic模式发送消息  订阅了该topic的客户都会收到

        Destination destination = new ActiveMQTopic(QueueConstant.queueName);
        for (int i = 0; i < 2; i++) {
            producer.sendMessage(destination, "myname is wsl!!!");
        }
    }
}
