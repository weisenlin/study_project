package com.wsl.study.enumeration;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/26 0026
 * 用途：对列名
 * 描述:
 */
public enum QueueEnum {

    WSL_QUEUE(101,"WSL_QUEUE");

    private Integer code;

    private String message;

    QueueEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
