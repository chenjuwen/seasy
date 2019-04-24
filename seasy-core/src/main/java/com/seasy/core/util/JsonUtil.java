package com.seasy.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtil {
	private static JsonConfig jsonConfig = null;
	
	static {
		jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
	}
	
	/**
     * 将json格式的字符串 转成 JSONObject对象
     */
    public static JSONObject string2JsonObject(String jsonData){
        if(StringUtil.isNotEmpty(jsonData)) {
            return JSONObject.fromObject(jsonData);
        }
        return null;
    }
    
    /**
     * 将json格式字符串 转成 JavaBean对象
     */
    public static <T> T string2JavaBean(String jsonString, Class<T> clazz){
        //key转小写
        Matcher matcher = Pattern.compile("\"([A-Za-z0-9]+)\":").matcher(jsonString);
        while(matcher.find()){
            jsonString = jsonString.replace(matcher.group(), matcher.group().toLowerCase());
        }

        JSONObject object = JSONObject.fromObject(jsonString);
        T bean = (T)JSONObject.toBean(object, clazz);
        return bean;
    }
	
	/**
	 * 将对象转成json格式的字符串：bean对象、Map对象
	 */
	public static String object2JsonString(Object object){
		if(object == null) {
			return "";
		}
		return JSONObject.fromObject(object, jsonConfig).toString();
	}
	
	/**
	 * 将集合对象转成json格式的字符串数组：List集合、Set集合
	 */
	public static String convertToJsonArrayString(Collection collection){
		if(collection == null) {
			return "[]";
		}
		return JSONArray.fromObject(collection, jsonConfig).toString();
	}
	
	/**
	 * 格式化json字符串
	 */
	public static String formatJsonString(String jsonStr){
		if(StringUtils.isEmpty(jsonStr)){
			return "";
		}
		
		JSONObject jsonObject = string2JsonObject(jsonStr);
		return jsonObject.toString(4);
	}
	
	/**
     * 从JSON对象中取得指定name对应的value值
     * @param object JSON对象
     * @param name 名称
     */
    public static String getValue(JSONObject object, String name){
        return getValue(object, name, "");
    }
    
    public static String getValue(JSONObject object, String name, String defaultValue){
        if(object == null || StringUtils.isEmpty(name)){
            return defaultValue;
        }

        try {
            if(object.has(name)){
                return object.getString(name);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return defaultValue;
    }
    
    public static String toJSONString(String key, String value){
        JSONObject object = new JSONObject();
        object.put(key, value);
        return object2JsonString(object);
    }

    public static String toJSONString(String key1, String value1, String key2, String value2){
        JSONObject object = new JSONObject();
        object.put(key1, value1);
        object.put(key2, value2);
        return object2JsonString(object);
    }

    public static String toJSONString(String key1, String value1, String key2, String value2, String key3, String value3){
        JSONObject object = new JSONObject();
        object.put(key1, value1);
        object.put(key2, value2);
        object.put(key3, value3);
        return object2JsonString(object);
    }

    public static String toJSONString(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4){
        JSONObject object = new JSONObject();
        object.put(key1, value1);
        object.put(key2, value2);
        object.put(key4, value4);
        return object2JsonString(object);
    }

    public static String toJSONString(String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5){
        JSONObject object = new JSONObject();
        object.put(key1, value1);
        object.put(key2, value2);
        object.put(key4, value4);
        object.put(key5, value5);
        return object2JsonString(object);
    }
	
	public static void main(String[] args) {
		JSONObject object = new JSONObject();
		object.put("uid", "cjm");
		object.put("pwd", "123");
		
		Map<String, String> map1 = new HashMap<>();
		map1.put("uid1", "cjm");
		map1.put("pwd1", "123");
		
		Map<String, String> map2 = new HashMap<>();
		map2.put("uid2", "cjm");
		map2.put("pwd2", "123");
		
		List<Map<String, String>> list = new ArrayList<>();
		list.add(map1);
		list.add(map2);
		
		System.out.println(formatJsonString("{\"uid1\":\"cjm\", \"pwd1\":\"123\"}"));
	}
	
}