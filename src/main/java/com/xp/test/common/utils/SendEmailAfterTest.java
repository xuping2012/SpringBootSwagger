package com.xp.test.common.utils;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Title: 使用javamail发送邮件 Description: 演示如何使用javamail包发送电子邮件。这个实例可发送多附件
 * 
 * @version 1.0
 */
public class SendEmailAfterTest {

	static String to = "joe.x@hcp.tech";// 收件人
	static String from = "joe.x@hcp.tech";// 发件人
	static String host = "smtp.mxhichina.com";// smtp主机
	static String username = "joe.x@hcp.tech";
	static String password = "xiaomi2A!";
	static String filename = ".\\test-output\\MyDefindEmailTestNGReport_2020-12-24-01-51.html";// 附件文件名
	static String subject = "发送测试报告邮件";// 邮件主题
	static String content = "UI自动化测试已完成，请查收结果！";// 邮件正文
	@SuppressWarnings("rawtypes")
	Vector file = new Vector();// 附件文件集合

	/**
	 * 方法说明：默认构造器 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	public SendEmailAfterTest() {
	}

	/**
	 * 方法说明：构造器，提供直接的参数传入 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public SendEmailAfterTest(String to, String from, String smtpServer,
			String username, String password, String subject, String content) {

		this.to = to;
		this.from = from;
		this.host = smtpServer;
		this.username = username;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * 方法说明：设置邮件服务器地址 <br>
	 * 输入参数：String host 邮件服务器地址名称 <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setHost(String host) {

		this.host = host;

	}

	/**
	 * 方法说明：设置登录服务器校验密码 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setPassWord(String pwd) {

		this.password = pwd;

	}

	/**
	 * 方法说明：设置登录服务器校验用户 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setUserName(String usn) {

		this.username = usn;

	}

	/**
	 * 方法说明：设置邮件发送目的邮箱 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setTo(String to) {

		this.to = to;

	}

	/**
	 * 方法说明：设置邮件发送源邮箱 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setFrom(String from) {

		this.from = from;

	}

	/**
	 * 方法说明：设置邮件主题 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setSubject(String subject) {
		this.subject = subject;

	}

	/**
	 * 方法说明：设置邮件内容 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("static-access")
	public void setContent(String content) {

		this.content = content;

	}

	/**
	 * 方法说明：把主题转换为中文 <br>
	 * 输入参数：String strText <br>
	 * 返回类型：
	 */

	public String transferChinese(String strText) {

		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"UTF-8"), "UTF-8", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	/**
	 * 方法说明：往附件组合中添加附件 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	@SuppressWarnings("unchecked")
	public void attachfile(String fname) {

		file.addElement(fname);

	}

	/**
	 * 方法说明：发送邮件 <br>
	 * 输入参数： <br>
	 * 返回类型：boolean 成功为true，反之为false
	 */

	@SuppressWarnings("rawtypes")
	public boolean sendMail() {
		// 构造mail session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			subject = transferChinese(subject);
			msg.setSubject(subject);
			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(content);
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);
			// 向Multipart添加附件
			Enumeration efile = file.elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(fds.getName());
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);
			}

			file.removeAllElements();
			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			// 发送邮件
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			System.out.println("==邮件发送失败==");
			return false;
		}
		System.out.println("==邮件发送成功==");
		return true;

	}

	/**
	 * 方法说明：主方法，用于测试 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */

	public static void main(String[] args) {

		SendEmailAfterTest sendmail = new SendEmailAfterTest();
		sendmail.setHost(host);// smtp.mail.yahoo.com.cn
		sendmail.setUserName(username);// 您的邮箱用户名
		sendmail.setPassWord(password);// 您的邮箱密码
		sendmail.setTo(to);// 接收者
		sendmail.setFrom(from);// 发送者
		sendmail.setSubject("你好，这是测试邮件");
		sendmail.setContent("你好这是一个带多附件的测试邮件");
		sendmail.attachfile(filename);
		sendmail.sendMail();
	}
}
