package com.seasy.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 格式化xml字符串
	 * @param xmlStr xml字符串
	 * @param encoding 编码格式
	 */
	public static String formatXML(String xmlStr, String encoding) throws Exception {  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(new StringReader(xmlStr));  
	    String resultXml = null;  
	    XMLWriter writer = null;  
	    if(document != null){
	    	try {  
	    		StringWriter stringWriter = new StringWriter();  
	    		OutputFormat format = new OutputFormat("    ", true, encoding);  
	    		writer = new XMLWriter(stringWriter, format);  
	    		writer.write(document);
	    		writer.flush();  
	    		resultXml = stringWriter.getBuffer().toString();  
	    	}finally{  
	    		if(writer != null){  
	    			try{  
	    				writer.close();
	    				writer = null;
	    			}catch(IOException ex){  
	    				ex.printStackTrace();
	    			}  
	    		}  
	    	}  
	    }  
	    return resultXml;  
	}
	
	/**
	 * 格式化xml字符串
	 * @param xmlStr xml字符串
	 */
	public static String formatXML(String xmlStr) throws Exception {
		return formatXML(xmlStr, DEFAULT_ENCODING);
	}

}
