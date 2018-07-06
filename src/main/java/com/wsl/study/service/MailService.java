package com.wsl.study.service;

/**
 * create by WSL_SILVA
 * 日期: 2018/7/6 0006
 * 用途：邮件发送服务类
 * 描述:
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
