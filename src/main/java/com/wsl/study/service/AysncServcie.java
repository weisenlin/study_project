package com.wsl.study.service;

import java.util.concurrent.Future;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/11 0011
 * 用途：
 * 描述:
 */
public interface AysncServcie {

    Future<String> runLoading() throws InterruptedException;
}
