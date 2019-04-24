package com.seasy.commons.freemarker;

import java.io.BufferedWriter;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ClassPathTemplateRender implements TemplateRender {
	private volatile static Configuration config = null;
	
	public static ClassPathTemplateRender getInstance(){
		return new ClassPathTemplateRender();
	}
	
	public ClassPathTemplateRender(){
		if(config == null){
			config = new Configuration(DefaultConfiguration.VERSION);
			config.setClassForTemplateLoading(this.getClass(), DefaultConfiguration.TEMPLATE_CLASSPATH);
			config.setDefaultEncoding(DefaultConfiguration.ENCODING);
			
	        try{
	        	config.setSetting("datetime_format", DefaultConfiguration.DATETIME_FORMAT);
	        	config.setSetting("number_format", DefaultConfiguration.NUMBER_FORMAT);
	        	config.setLocale(DefaultConfiguration.LOCALE);
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
		}
	}
	
	public String render(Object dataModel, String ftlFile) throws Exception {
		StringWriter stringWriter = null;
		BufferedWriter bufferedWriter = null;
		String content = null;
		try{
			stringWriter = new StringWriter();
	        bufferedWriter = new BufferedWriter(stringWriter);
	        
	        Template template = config.getTemplate(ftlFile);
	        template.process(dataModel, bufferedWriter);
	        
	        bufferedWriter.flush();
	        
	        content = stringWriter.toString();
		}finally{
			if(stringWriter != null){
				stringWriter.close();
				stringWriter = null;
			}
			if(bufferedWriter != null){
				bufferedWriter.close();
				bufferedWriter = null;
			}
		}
        return content;
	}

}
