package com.wsl.study.message.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/26 0026
 * 用途：
 * 描述:
 */
@Service("producer")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String message){
        jmsTemplate.convertAndSend(destination,message);
    }
}
