package com.wsl.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * create by WSL_SILVA
 * 日期: 2018/11/28 0028
 * 用途：线程池
 * 描述:
 */
public class ExcutorThreadDemo {

    public static void main(String[] args){
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        for(int i=0;i<100;i++){
            executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("name:"+Thread.currentThread().getName());
                }
            },1, TimeUnit.SECONDS);
        }
        executorService.shutdown();
    }
}
