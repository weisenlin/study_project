package com.wsl.study.service.impl;

import com.wsl.study.service.AysncServcie;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/11 0011
 * 用途：
 * 描述:
 */
@Service
@Transactional
public class AysncServiceImpl implements AysncServcie {

    @Override
    @Async
    public Future<String> runLoading() throws InterruptedException {
        System.out.println("开始=================");
        Thread.sleep(10000);
        System.out.println("结束=================");
        return new AsyncResult<>("test");
    }
}
