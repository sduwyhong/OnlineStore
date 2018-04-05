package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import domain.Customer;

public class SendActivateEmail extends Thread {

	private Customer c;
	private String contextPath;

	public SendActivateEmail(Customer c, String contextPath) {
		this.c = c;
		this.contextPath = contextPath;
	}

	@Override
	public void run() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.163.com");//the local machine
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("17864154784@163.com"));
			message.setRecipients(Message.RecipientType.TO, c.getEmail());
			message.setSubject("在线书店激活邮件");
			//异常javax.mail.internet.ParseException: Expected ';', got ","
			//原因"text/html;charset=UTF-8"中间写成逗号
			message.setContent("欢迎您成为我们的会员！<br/>请点击<a href='http://localhost:8080"+contextPath+"/client/ClientServlet?op=activate&id="+c.getId()+"&code="+c.getCode()+"'>激活</a>来激活您的账户。由于本邮件由系统发出，请不要回复。","text/html;charset=UTF-8");
			System.out.println("http://localhost8080"+contextPath+"/client/ClientServlet?op=activate&id="+c.getId()+"&code="+c.getCode()+"");
			message.saveChanges();
			
			Transport ts = session.getTransport();
			//账号：邮箱帐号 密码：授权码
			//qq-pop3/smtp:axljnfhqhttvcaii
			//qq-IMAP/SMTP:trrrhrkqpkiocbaf
			ts.connect("17864154784", "wangyuhong123");
			ts.sendMessage(message, message.getAllRecipients());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
