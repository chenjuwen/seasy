package com.seasy.commons.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.seasy.core.SeasyConstants;
import com.seasy.core.util.PropertiesUtil;

public class MailFactory {
	private JavaMailSender javaMailSender;
	
	public MessageWraper getMessageWraper()throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		return new MessageWraper(mimeMessageHelper);
	}
	
	/**
	 * 发送邮件
	 */
	public void send(MessageWraper messageWraper) throws Exception{
		Properties props = System.getProperties();
		
		//Proxy
		PropertiesUtil propUtil = PropertiesUtil.getInstance(MailConstants.MAIL_CONFIG_FILE);
		String proxyEnabled = propUtil.getProperty(SeasyConstants.PROXY_ENABLED, "false");
		if("true".equalsIgnoreCase(proxyEnabled)){
			String proxyHost = propUtil.getProperty(SeasyConstants.PROXY_HOST);
			String proxyPort = propUtil.getProperty(SeasyConstants.PROXY_PORT);

			props.put("http.proxySet", "true");
			props.put("http.proxyHost", proxyHost);
			props.put("http.proxyPort", proxyPort);
		}else{
			props.put("http.proxySet", "false");
		}
		
		javaMailSender.send(messageWraper.getMessage()); 		
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
}
