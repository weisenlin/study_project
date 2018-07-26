package com.wsl.study.message.consumer;

import com.wsl.study.constant.QueueConstant;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/26 0026
 * 用途：
 * 描述:
 */
@Component
public class Consumer {

    @JmsListener(destination = QueueConstant.queueName)
    public void receiveQueue(String text){
        System.out.println("Consumer收到的报文为:"+text);
    }
}
