package com.seasy.commons.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.seasy.core.util.PropertiesUtil;

public class MessageWraper {
	private MimeMessageHelper messageHelper;

	public MessageWraper(MimeMessageHelper messageHelper){
		this.messageHelper = messageHelper;
	}
	
	public MimeMessage getMessage() {
		return messageHelper.getMimeMessage();
	}

	public MimeMessageHelper getMessageHelper() {
		return messageHelper;
	}
	
	public void setFrom(String from)throws MessagingException{
		messageHelper.setFrom(from);
	}
	
	public void setTo(String[] toArray)throws MessagingException{
		messageHelper.setTo(toArray);
	}
	
	public void setCc(String[] ccArray)throws MessagingException{
		messageHelper.setCc(ccArray);
	}
	
	public void setSubject(String subject)throws MessagingException{
		messageHelper.setSubject(subject);
	}
	
	public void setText(String text, boolean isHtml) throws MessagingException,UnsupportedEncodingException {
		messageHelper.setText(text, isHtml);
	}
	
	public void setText(String text) throws MessagingException,UnsupportedEncodingException {
		messageHelper.setText(text, false);
	}
	
	public void setSentDate(Date date)throws MessagingException{
		if(date == null) date = new Date();
		messageHelper.setSentDate(date);
	}
	
	public void addAttachment(File file) throws MessagingException,UnsupportedEncodingException {
		if(file == null) {
			return;
		}
		String fileName = encodingContent(file.getName());
		messageHelper.addAttachment(fileName, file);
	}
	
	private String encodingContent(String content) throws UnsupportedEncodingException {
		PropertiesUtil propUtil = PropertiesUtil.getInstance();
		String charset = propUtil.getProperty(MailConstants.MAIL_MIME_CHARSET, MailConstants.DEFAULT_CHARSET);
		String mimeEncoding = propUtil.getProperty(MailConstants.MAIL_MIME_ENCODING, MailConstants.DEFAULT_ENCODING);

		//encoding: 通常对邮件头的编码方式有2种,一种是B（base64）方式编码，一种是QP（quoted-printable）方式编码
		String returnValue = MimeUtility.encodeText(content, charset, mimeEncoding);
		return returnValue;
	}
	
}
