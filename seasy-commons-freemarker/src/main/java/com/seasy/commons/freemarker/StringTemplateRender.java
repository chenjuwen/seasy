package com.seasy.commons.freemarker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class StringTemplateRender implements TemplateRender {
	private volatile static Configuration config = null;
	
	public static StringTemplateRender getInstance(){
		return new StringTemplateRender();
	}
	
	public StringTemplateRender(){
		if(config == null){
			config = new Configuration(DefaultConfiguration.VERSION);
			config.setTemplateLoader(new StringTemplateLoader());
			config.setDefaultEncoding(DefaultConfiguration.ENCODING);
    		config.setLocale(DefaultConfiguration.LOCALE);
			
			try{
	    		config.setSetting("datetime_format", DefaultConfiguration.DATETIME_FORMAT);
	        	config.setSetting("number_format", DefaultConfiguration.NUMBER_FORMAT);
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
		}
	}
	
	public String render(Object dataMap, String text) throws Exception {
		String content = null;
		
		if((dataMap instanceof Map) == false){
			throw new IllegalArgumentException("Parameter dataModel must be a Map");
		}
		
        BufferedReader reader = new BufferedReader(new StringReader(text));
        Template template = new Template(null, reader, config);

        BeansWrapperBuilder builder = new BeansWrapperBuilder(DefaultConfiguration.VERSION);
        SimpleHash dataModel = new SimpleHash(builder.build());
        dataModel.putAll((Map)dataMap);
        
        StringWriter stringWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            stringWriter = new StringWriter();
            bufferedWriter = new BufferedWriter(stringWriter);
            template.process(dataModel, bufferedWriter);
            bufferedWriter.flush();
            
            content = stringWriter.toString();
            
        }catch(Exception ex){
        	ex.printStackTrace();
            content = null;
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

	class StringTemplateLoader implements TemplateLoader {
	    public void closeTemplateSource(Object templateSource) throws IOException {
	        return;
	    }
	    
	    public Object findTemplateSource(String name) throws IOException {
	        return name;
	    }
	    
	    public long getLastModified(Object templateSource) {
	        return System.currentTimeMillis();
	    }
	    
	    public Reader getReader(Object templateSource, String encoding) throws IOException {
	        String ftlString = (String)templateSource;
	        return new StringReader(ftlString);
	    }
	}
	
}
