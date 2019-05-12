package org.muses.jeeplatform.core.email;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;


public class JavaEmailSender {

        public boolean sendTextMail(EmailInfo emailinfo)throws Exception{
                MyAuthenticator auhenticator = null;
                Properties pro = emailinfo.getProperties();
                //如果需要身份认证，则创建一个密码验证器
                if(emailinfo.isValidate()){
                        auhenticator = new MyAuthenticator(emailinfo.getUsername(),emailinfo.getPassword());
                }
                //根据邮件会话属性和密码验证器构造一个发送邮件的session
                Session session = Session.getDefaultInstance(pro,auhenticator);
                //根据Session创建一个邮件消息
                Message message = new MimeMessage(session);
                //创建邮件发送者地址
                Address fromAddress = new InternetAddress(emailinfo.getMailFormAddress());
                //设置邮件消息的发送者
                message.setFrom(fromAddress);
                //创建邮件接收者地址
                Address toAddress = new InternetAddress(emailinfo.getMailToAddress());
                //设置邮件消息的接收者
                message.setRecipient(Message.RecipientType.TO,toAddress);
                //设置邮件发送时间
                message.setSentDate(new Date());
                // 设置邮件消息的主题
                message.setSubject(emailinfo.getSubject());
                // 设置邮件消息发送的时间
                message.setSentDate(new Date());
                // 设置邮件消息的主要内容
                String mailContent = emailinfo.getContent();
                message.setText(mailContent);
                // 发送邮件
                Transport.send(message);
                return true;
        }

        public boolean sendHtmlMail(EmailInfo emailinfo)throws Exception{
                MyAuthenticator auhenticator = null;
                Properties pro = emailinfo.getProperties();
                //如果需要身份认证，则创建一个密码验证器
                if(emailinfo.isValidate()){
                        auhenticator = new MyAuthenticator(emailinfo.getUsername(),emailinfo.getPassword());
                }
                //根据邮件会话属性和密码验证器构造一个发送邮件的session
                Session session = Session.getDefaultInstance(pro,auhenticator);
                //根据Session创建一个邮件消息
                Message message = new MimeMessage(session);
                //创建邮件发送者地址
                Address fromAddress = new InternetAddress(emailinfo.getMailFormAddress());
                //设置邮件消息的发送者
                message.setFrom(fromAddress);
                //创建邮件接收者地址
                Address toAddress = new InternetAddress(emailinfo.getMailToAddress());
                //设置邮件消息的接收者
                message.setRecipient(Message.RecipientType.TO,toAddress);
                //设置邮件发送时间
                message.setSentDate(new Date());
                // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
                Multipart multiPart = new MimeMultipart();
                // 创建一个包含HTML内容的MimeBodyPart
                BodyPart html = new MimeBodyPart();
                // 设置HTML内容
                html.setContent(emailinfo.getContent(), "text/html; charset=utf-8");
                multiPart.addBodyPart(html);
                // 将MiniMultipart对象设置为邮件内容
                message.setContent(multiPart);
                // 发送邮件
                Transport.send(message);
                return true;
        }


        public static void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception {
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
                builder.append("\n" + emailContent);
                builder.append("\n时间 " + new Date());
                msg.setText(builder.toString());
                msg.setFrom(new InternetAddress("nickypm@foxmail.com"));

                Transport transport = session.getTransport();
                transport.connect("smtp.qq.com", "nickypm@foxmail.com", "xoguhfiomzenbged");

                transport.sendMessage(msg, new Address[]{new InternetAddress(toEmailAddress)});
                transport.close();
        }


}
