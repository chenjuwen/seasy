package com.seasy.demo.aloneapp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 为了解决文件名包含中文字符时出现乱码的问题，需要
 * 		1、在TOMCAT_HOME/config/server.xml文件中的Connector元素增加 URIEncoding="UTF-8" 属性
 * 		2、在页面调用方法
 * 			<a href="<%=ctx%>/download?filename=<%=URLEncoder.encode("/download/maven学习.txt", "UTF-8")%>">maven学习.txt</a>
 */
@Controller
public class DownloadController {
	private static final String ENCODING_UTF8 = "UTF-8";
	private static final String ENCODING_ISO88591 = "iso-8859-1";
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) {
        BufferedInputStream bis = null; 
        BufferedOutputStream bos = null;
        try {
            request.setCharacterEncoding(ENCODING_UTF8);
            
            //file name
        	String filename = StringUtils.trimToEmpty(request.getParameter("filename"));
        	//System.out.println("filename = " + filename);
        	
        	//file full path
        	String fileFullPath = request.getServletContext().getRealPath(filename);
            System.out.println("fileFullPath = " + fileFullPath);
            
            //file length
            File file = new File(fileFullPath);
            String simpleFileName = file.getName();
            long fileLength = file.length(); 

            String userAgentString = request.getHeader("USER-AGENT");
            simpleFileName = encodeFileName(simpleFileName, userAgentString);
            //System.out.println("simpleFileName = " + simpleFileName);

            response.reset();
            response.setHeader("Pragma", "public");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            
            response.setHeader("Content-disposition", "attachment; filename=" + simpleFileName);
            response.setHeader("Content-Length", String.valueOf(fileLength)); 
            response.setContentType("application/octet-stream");
            
            bis = new BufferedInputStream(new FileInputStream(fileFullPath));  
            bos = new BufferedOutputStream(response.getOutputStream());
            
            IOUtils.copy(bis, bos);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {
        	IOUtils.closeQuietly(bis);
        	IOUtils.closeQuietly(bos);
        }
	}
	
	/**
	 * 文件名编码处理
	 */
	private String encodeFileName(String filename, String userAgentString){
		String encodedFilename = null;  
		try {
			//System.out.println("userAgentString = " + userAgentString);
			if(StringUtils.isNotEmpty(userAgentString)){
				if(userAgentString.toLowerCase().indexOf("msie") > -1 
						|| userAgentString.toLowerCase().indexOf("trident") > -1){ //IE
					encodedFilename = URLEncoder.encode(filename, ENCODING_UTF8); 
					
				}else{
					encodedFilename = new String(filename.getBytes(ENCODING_UTF8), ENCODING_ISO88591);
				}
			}else{
				encodedFilename = new String(filename.getBytes(ENCODING_UTF8), ENCODING_ISO88591);
			}
           
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return encodedFilename;
	}
	
}
