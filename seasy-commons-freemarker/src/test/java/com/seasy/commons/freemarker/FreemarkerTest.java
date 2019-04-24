package com.seasy.commons.freemarker;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FreemarkerTest {
	@Test
	public void renderFromString(){
		try {
			String text = "hello ${username}";
			
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("username", "张三");
			
			StringTemplateRender render = StringTemplateRender.getInstance();
			String content = render.render(dataMap, text);
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void renderFromClasspath(){
		try {
			Map<String, String> dataModel = new HashMap<String, String>();
			dataModel.put("username", "张三");
			
			ClassPathTemplateRender render = ClassPathTemplateRender.getInstance();
			String content = render.render(dataModel, "test.ftl");
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
