package com.seasy.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EntityUtil {
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 将bean对象转为map对象
	 */
	public static Map<String, String> entity2Map(Object obj){
		if(obj == null){  
            return null;  
        }
		
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] arr = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor propDesc : arr) {  
                String key = propDesc.getName();  
   
                if (!"class".equalsIgnoreCase(key)) {
                    Method getter = propDesc.getReadMethod();  
                    Object objectValue = getter.invoke(obj);
                    map.put(key.toUpperCase(), convertToStringValue(objectValue)); //key大写
                }
            }
        } catch (Exception ex) {  
            ex.printStackTrace();
        } 
        
        return map;  
	}
	
	/**
	 * 将各种对象类型的值转为字符串值
	 * @param objectValue 对象类型的值
	 */
	private static String convertToStringValue(Object objectValue) {
		if(objectValue == null) {
			return null;
		}
		
		String typeName = objectValue.getClass().getSimpleName();
		
		if("String".equals(typeName)){
			return String.valueOf(objectValue);
		}else if("Long".equals(typeName)){
			return objectValue.toString();
		}else if("Integer".equals(typeName)){
			return objectValue.toString();
		}else if("Double".equals(typeName)){
			return objectValue.toString();
		}else if("Float".equals(typeName)){
			return objectValue.toString();
		}else if("Boolean".equals(typeName)){
			return objectValue.toString();
		}else if("Short".equals(typeName)){
			return objectValue.toString();
		}else if("Byte".equals(typeName)){
			return objectValue.toString();
		}else if("Date".equals(typeName)){
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Date date = (Date)objectValue;
			return sdf.format(date);
		}else{
			return String.valueOf(objectValue);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(convertToStringValue("123.45"));
		System.out.println(convertToStringValue(new Long(123)));
		System.out.println(convertToStringValue(new Integer(234)));
		System.out.println(convertToStringValue(new Double(123.46)));
		System.out.println(convertToStringValue(new Float(123.47)));
		System.out.println(convertToStringValue(new Boolean(true)));
		System.out.println(convertToStringValue(new Short("345")));
		System.out.println(convertToStringValue(new Byte("456".getBytes()[0])));
		System.out.println(convertToStringValue(new Date()));
	}
	
}
