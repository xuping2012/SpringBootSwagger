package com.xp.test.common.utils;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Date;


public class SendMail {

    /**
     * 1、SMTP 服务器的端口 (不同的服务器可能会有不同端口号)
     * 2、会话对象，用于和邮件服务器交互
     * 3、邮件对象
     */

    public static final String smtpPort = "465";

    private Session session;

    private MimeMessage message;

    public SendMail() { }

    /**
     * 初始化方法
     *
     * @param myEmailSMTPHost 发件人邮箱 SMTP 地址
     * @param agreement       使用的协议
     * @param auth            是否需要请求认证
     */
    public SendMail(String myEmailSMTPHost, String agreement, String auth) throws NoSuchProviderException {

        // 配置参数
        Properties properties = new Properties();

        properties.setProperty("mail.transport.protocol", agreement);

        properties.setProperty("mail.smtp.host", myEmailSMTPHost);

        properties.setProperty("mail.smtp.auth", auth);

        properties.setProperty("mail.smtp.port", smtpPort);

        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 获取一个邮件会话对象
        session = Session.getInstance(properties);

        // 是否需要开启 debug 模式，开启后会输出详细的 log 信息
        session.setDebug(true);

        // 创建一封邮件
        message = new MimeMessage(session);
    }

    /**
     * 设置邮件内容
     *
     * @param myEmailAccout  发件人邮箱
     * @param addresseeEmail 收件人邮箱
     * @param emailSubject   邮件主题
     * @param emailContent   邮件正文(PS：可以是 HTML 标签形式)
     * @return MimeMessage  邮件
     * @throws Exception
     */
    public MimeMessage setEmail(String myEmailAccout, String addresseeEmail, String emailSubject, String emailContent) throws MessagingException {
        message.setFrom(myEmailAccout);

        // 第一个参数 以 收件人、抄送、密送 的形式，具体意思请自行百度（手动捂脸）
        message.setRecipients(Message.RecipientType.TO, addresseeEmail);

        message.setSubject(emailSubject, "UTF-8");

        message.setContent(emailContent, "text/html;charset=UTF-8");

        // 邮件发送时间
        message.setSentDate(new Date());

        // 保存设置内容
        message.saveChanges();

        return message;
    }

    /**
     * 发送邮件
     *
     * @param myEmailAccout   发件人邮箱
     * @param myEmailPassword 发件人邮箱授权码（163 邮箱的叫做独立密码）
     * @throws MessagingException
     */
    public void sendOut(String myEmailAccout, String myEmailPassword) throws MessagingException {
        // 第一步：通过邮件会话对象（Session）获取邮件传输对象
        Transport transport = session.getTransport();

        // 第二步：进行连接
        transport.connect(myEmailAccout, myEmailPassword);

        // 第三步：发送邮件，第一个参数是：邮件对象（MimeMessage），第二个参数获取保存设置后的邮件内容
        transport.sendMessage(message, message.getAllRecipients());

        // 第三步：关闭连接。
        transport.close();
    }
    
    
    public static void main(String[] args) throws MessagingException {
        String myEmailAccount = "joe.x@hcp.tech";

        String myEmailPassword = "xiaomi2A!"; //这个就是授权码

        // 每个邮箱的地址有所不同
        String myEmailSMTPHost = "smtp.mxhichina.com";

        String addresseeEmail = "joe.x@hcp.tech";

        String agreement = "smtp";

        String auth = "true";

        String emailSubject = "元宵节快乐";

        String emailContent = "<h3>fsdafdsfsdaf</h3>";

        SendMail mail = new SendMail(myEmailSMTPHost, agreement, auth);
        mail.setEmail(myEmailAccount, addresseeEmail, emailSubject, emailContent);
        mail.sendOut(myEmailAccount, myEmailPassword);
    }

}