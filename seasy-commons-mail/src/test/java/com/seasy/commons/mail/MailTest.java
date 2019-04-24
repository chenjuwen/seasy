package com.seasy.commons.mail;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mail.xml"})
public class MailTest {
	@Autowired
	private MailFactory mailFactory;
	
	@Test
	public void mailSend() throws Exception {
		MessageWraper messageWraper = mailFactory.getMessageWraper();
			
		messageWraper.setFrom("test@163.com");
		messageWraper.setTo(new String[]{"test@qq.com"});
		messageWraper.setSubject("邮件标题");
		messageWraper.setText("邮件内容<b>大写</b>", true);
		messageWraper.setSentDate(new Date());
		messageWraper.addAttachment(new File("e:\\大数据.txt"));
		
		mailFactory.send(messageWraper);
	}
	
	@Test
	public void simpleMailSend() throws Exception {
		SimpleMessageWraper messateWraper = SimpleMailFactory.getMessageWraper(); 
		
		messateWraper.setFrom("test@163.com"); 
		messateWraper.setTo(new String[]{"test@qq.com"});
		messateWraper.setSubject("邮件标题");
		messateWraper.setText("邮件内容<b>大写</b><b>大写</b>");
		messateWraper.setSentDate(new Date());
		messateWraper.addAttactment(new File("e:\\大数据.txt"));
		
		SimpleMailFactory.send(messateWraper);
	}
	
}
