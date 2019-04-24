package com.seasy.commons.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.seasy.core.util.PropertiesUtil;

public class SimpleMessageWraper {
	private Session session;
	private MimeMessage mimeMessage;
	private Multipart multipart = new MimeMultipart();;

	public SimpleMessageWraper(Properties props, final String username, final String password){
        session = Session.getDefaultInstance(props,
                new Authenticator() {
		            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		                return new javax.mail.PasswordAuthentication(username, password);
		             }
		        });
        
		mimeMessage = new MimeMessage(session);
	}
	
	public void setFrom(String from)throws MessagingException{
		mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.saveChanges();
	}
	
	public void setTo(String[] toArray)throws MessagingException{
		String s = arrayToString(toArray);
		Address[] addresses = InternetAddress.parse(s);
		mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
        mimeMessage.saveChanges();
	}
	
	public void setCc(String[] ccArray)throws MessagingException{
		String s = arrayToString(ccArray);
		Address[] addresses = InternetAddress.parse(s);
		mimeMessage.setRecipients(Message.RecipientType.CC, addresses);
        mimeMessage.saveChanges();
	}
	
	public void setSubject(String subject)throws MessagingException{
		mimeMessage.setSubject(subject);
        mimeMessage.saveChanges();
	}

    public void setText(String text)throws MessagingException{
    	MimeBodyPart mimeBodyPart = new MimeBodyPart(); 
    	mimeBodyPart.setText(text);
    	
    	multipart.addBodyPart(mimeBodyPart);
    	mimeMessage.setContent(multipart);
        mimeMessage.saveChanges();
    }
	
	public void setSentDate(Date date)throws MessagingException{
		mimeMessage.setSentDate(date);
        mimeMessage.saveChanges();
	}
    
    public void addAttactment(File file)throws MessagingException,UnsupportedEncodingException{
    	if(file == null) {
    		return;
    	}
    	
    	MimeBodyPart mimeBodyPart = new MimeBodyPart(); 

		PropertiesUtil propUtil = PropertiesUtil.getInstance();
		String charset = propUtil.getProperty(MailConstants.MAIL_MIME_CHARSET, MailConstants.DEFAULT_CHARSET);
		String mimeEncoding = propUtil.getProperty(MailConstants.MAIL_MIME_ENCODING, MailConstants.DEFAULT_ENCODING);

		//encoding: 通常对邮件头的编码方式有2种,一种是B（base64）方式编码，一种是QP（quoted-printable）方式编码
    	mimeBodyPart.setFileName(MimeUtility.encodeText(file.getName(), charset, mimeEncoding)); 
    	mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(file))); 
    	multipart.addBodyPart(mimeBodyPart); 
    	mimeMessage.setContent(multipart);
        mimeMessage.saveChanges();
    }
	
	public Session getSession() {
		return session;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}
	
	private String arrayToString(String[] array){
		String s = "";
		if(array != null && array.length > 0){
			for(int i=0; i<array.length; i++){
				if(s == ""){
					s = array[i].trim();
				}else{
					s += "," + array[i].trim();
				}
			}
		}
		return s;
	}
	
}
