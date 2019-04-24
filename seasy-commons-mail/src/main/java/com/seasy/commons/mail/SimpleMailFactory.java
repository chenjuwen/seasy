package com.seasy.commons.mail;

import java.util.Properties;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.seasy.core.SeasyConstants;
import com.seasy.core.util.PropertiesUtil;

public class SimpleMailFactory {
	/**
	 * 取得邮件消息对象的包装器
	 */
	public static SimpleMessageWraper getMessageWraper(){
		return generateMessageWraper();
	}

	private static SimpleMessageWraper generateMessageWraper() {
		//mail paramter
		PropertiesUtil propUtil = PropertiesUtil.getInstance();
		String proxyEnabled = propUtil.getProperty(SeasyConstants.PROXY_ENABLED, "false");
		String proxyHost = propUtil.getProperty(SeasyConstants.PROXY_HOST);
		String proxyPort = propUtil.getProperty(SeasyConstants.PROXY_PORT);

		PropertiesUtil mailPropUtil = PropertiesUtil.getInstance(MailConstants.MAIL_CONFIG_FILE);
		String mailSmtpHost = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_HOST);
		String mailSmtpUsername = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_USERNAME);
		String mailSmtpPassword = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_PASSWORD);
		String mailSmtpPort = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_PORT, "25");
		String mailTransportProtocol = mailPropUtil.getProperty(MailConstants.MAIL_TRANSPORT_PROTOCOL, "smtp");
		String mailSmtpAuth = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_AUTH, "true");
		String mailDebug = mailPropUtil.getProperty(MailConstants.MAIL_DEBUG, "false");
		String mailSmtpTimeout = mailPropUtil.getProperty(MailConstants.MAIL_SMTP_TIMEOUT, "5000");

		Properties props = System.getProperties();
		if("true".equalsIgnoreCase(proxyEnabled)){
			props.put("http.proxySet", "true");
			props.put("http.proxyHost", proxyHost);
			props.put("http.proxyPort", proxyPort);
		}else{
			props.put("http.proxySet", "false");
		}
		
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.debug", mailDebug);
        props.put("mail.transport.protocol", mailTransportProtocol);
        props.put("mail.smtp.timeout", mailSmtpTimeout);
		
        SimpleMessageWraper messageWraper = new SimpleMessageWraper(props, mailSmtpUsername, mailSmtpPassword);
        return messageWraper;
	}

	/**
	 * 邮件发送
	 */
	public static void send(SimpleMessageWraper messageWraper)throws Exception{
		MimeMessage mimeMessage = messageWraper.getMimeMessage();
		Transport.send(mimeMessage);
	}
	
}
