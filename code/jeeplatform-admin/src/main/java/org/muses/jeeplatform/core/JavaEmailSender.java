package org.muses.jeeplatform.core;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaEmailSender {


	public static void sendEmail(String toEmailAddress,String emailTitle,String emailContent)throws Exception{
		Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject(emailTitle);
        StringBuilder builder = new StringBuilder();
        builder.append("url = " + "http://blog.csdn.net/");
        builder.append("\n"+emailContent);
        builder.append("\n时间 " + new Date());
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress("*"));

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "nickypm@foxmail.com", "futbsjjelyhjcaci");

        transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });
        transport.close();
	}


}
