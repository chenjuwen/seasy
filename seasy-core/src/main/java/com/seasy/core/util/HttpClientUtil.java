package com.seasy.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.seasy.core.SeasyConstants;

import net.sf.json.JSONObject;

public class HttpClientUtil {
	public static final int DEFAULT_TIMEOUT = 5000;
	
	/**
	 * 以get方式访问
	 * @param url url地址
	 */
	public static String get(String url) {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String result = null;
        try {
        	//HttpClient
        	httpClient = HttpClients.createDefault();
        	
        	//HttpGet
        	httpGet = new HttpGet(url); 
        	httpGet.setConfig(buildRequestConfig());  
        	
        	//Response
        	response = httpClient.execute(httpGet); 
            int statusCode = response.getStatusLine().getStatusCode();  
              
            if (statusCode == HttpStatus.SC_OK) { 
                HttpEntity httpEntity = response.getEntity(); 
                result = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity); 
            }
            
        } catch (Exception ex) {  
            ex.printStackTrace();
        } finally {  
            try {
            	if(response != null){
            		response.close();
            	}
            	
            	httpGet.releaseConnection();
            } catch (Exception ex) {  
            	ex.printStackTrace();
            }  
        } 
        return result;
	}
	
	/**
	 * 以post方式访问
	 * @param url url地址
	 * @param params 参数集合
	 */
	public static String post(String url, List<NameValuePair> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		String result = null;
        try {
        	if(params == null){
        		params = new ArrayList<NameValuePair>();
        	}
        	
        	//HttpClient
        	httpClient = HttpClients.createDefault();
        	
        	//HttpPost
            httpPost = new HttpPost(url); 
            httpPost.setConfig(buildRequestConfig());
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            
            //Response
            response = httpClient.execute(httpPost); 
            int statusCode = response.getStatusLine().getStatusCode();  
              
            if (statusCode == HttpStatus.SC_OK) { 
                HttpEntity httpEntity = response.getEntity(); 
                result = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity); 
            }
            
        } catch (Exception ex) {  
            ex.printStackTrace();
        } finally {  
            try {
            	if(response != null){
            		response.close();
            	}
            	
            	httpPost.releaseConnection();
            } catch (Exception ex) {  
            	ex.printStackTrace();
            }  
        } 
        return result;
	}

	private static RequestConfig buildRequestConfig() {
		//Builder
		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setConnectionRequestTimeout(DEFAULT_TIMEOUT)
			.setConnectTimeout(DEFAULT_TIMEOUT)
			.setSocketTimeout(DEFAULT_TIMEOUT);

		//Proxy
		PropertiesUtil propUtil = PropertiesUtil.getInstance();
		String proxyEnabled = propUtil.getProperty(SeasyConstants.PROXY_ENABLED, "false");
		if("true".equalsIgnoreCase(proxyEnabled)){
			String proxyHost = propUtil.getProperty(SeasyConstants.PROXY_HOST);
			String proxyPort = propUtil.getProperty(SeasyConstants.PROXY_PORT);

			HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort)); 
			builder.setProxy(proxy);
		}
		
		return builder.build();
	}
	
	public static void main(String[] args) {
		String url = "";
		String s = "";
		try{
			url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=14.215.42.188";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", "uid"));
			s = post(url, params);
			
			System.out.println(s);
			JSONObject data = JSONObject.fromObject(s);
			System.out.println(s);
			System.out.println(data.getString("country"));
			System.out.println(data.getString("province"));
			System.out.println(data.getString("city"));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
