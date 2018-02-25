package com.Tblog.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;
@Component
public class MailUtils {
	// 发送邮件的方法
	public void sendMail(String to,String subtitle,String contents) throws Exception {
		// 创建连接对象，链接到服务器
		Properties prop = new Properties();
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@tblog.com", "service");
			}
		});

		
		//创建邮件对象
		Message message = new MimeMessage(session);
		//创建发件人
		message.setFrom(new InternetAddress("service@tblog.com"));
		//设置收件人
		message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
		message.setSubject(subtitle);//标题
		message.setContent(contents,"text/html;charset=utf-8");//内容
		//发送邮件
		Transport.send(message);
		
	}
}