package com.seasy.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware, DisposableBean{
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		checkApplicationContext();
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		checkApplicationContext();
		return (T)applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		checkApplicationContext();
		return applicationContext.getBean(clazz);
	}
	
	public static <T> T getBean(String name, Class<T> clazz){
		checkApplicationContext();
		return applicationContext.getBean(name, clazz);
	}
	
	@Override
	public void destroy() throws Exception {
		applicationContext = null;
	}
	
	private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext is null");
        }
    }
	
}
