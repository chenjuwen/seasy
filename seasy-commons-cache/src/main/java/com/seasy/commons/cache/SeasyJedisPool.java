package com.seasy.commons.cache;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.log4j.Logger;

import com.netflix.config.ConfigurationManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class SeasyJedisPool implements SeasyJedisPoolConfig {
	private static Logger log = Logger.getLogger(SeasyJedisPool.class);
	
	private static JedisPool jedisPool = null;
	private static AbstractConfiguration jedisConfiguration = null;
	private static final String JEDIS_CONFIG_PROPERTIES_FILENAME = "jedis-config.properties";
	
	public static Jedis getResource(){
		if(jedisPool == null){
			try{
				buildPool();
			}catch(Exception ex){
				writeLog("Build Jedis Pool Error", ex);
			}
		}
		
		Jedis resource = null;
		try{
			resource = jedisPool.getResource();
		}catch(Exception ex){
			returnResource(resource);
			writeLog("Get Resource from Jedis Pool error", ex);
		}
		return resource;
	}
	
	public static void returnResource(final Jedis resource) {
		try{
			if(resource != null){
				resource.close();
			}
		}catch(Exception ex){
			if(resource != null && resource.isConnected()){
				resource.quit();
				resource.disconnect();
			}
			writeLog("Return Resource to Jedis Pool error", ex);
		}
	}
	
	private static void buildPool() throws Exception {
		if(jedisPool == null){
			synchronized (SeasyJedisPool.class) {
				if(jedisPool == null){
					createJedisPoolInstance();
				}
			}
		}
	}
	
	private static void createJedisPoolInstance() throws Exception {
		loadJedisConfiguration();

		if(jedisConfiguration != null){
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(jedisConfiguration.getInt(MAXTOTAL, DEFAULT_MAXTOTAL));
			poolConfig.setMinIdle(jedisConfiguration.getInt(MINIDLE, DEFAULT_MINIDLE));
			poolConfig.setMaxIdle(jedisConfiguration.getInt(MAXIDLE, DEFAULT_MAXIDLE));
			poolConfig.setMaxWaitMillis(jedisConfiguration.getLong(MAX_WAIT_MILLIS, DEFAULT_MAX_WAIT_MILLIS));
			poolConfig.setTestOnBorrow(jedisConfiguration.getBoolean(TEST_ON_BORROW, DEFAULT_TEST_ON_BORROW));
			poolConfig.setTestOnReturn(jedisConfiguration.getBoolean(TEST_ON_RETURN, DEFAULT_TEST_ON_RETURN));
			
			String host = jedisConfiguration.getString(HOST);
			int port = jedisConfiguration.getInt(PORT, DEFAULT_PORT);
			int timeout = jedisConfiguration.getInt(TIMEOUT, DEFAULT_TIMEOUT);
			String password = jedisConfiguration.getString(PASSWORD);
			
			jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
		}else{
			jedisPool = new JedisPool();
		}
	}
	
	private static void loadJedisConfiguration(){
		if(jedisConfiguration == null){
			try {
				ConfigurationManager.loadPropertiesFromResources(JEDIS_CONFIG_PROPERTIES_FILENAME);
				jedisConfiguration = ConfigurationManager.getConfigInstance();
			} catch (IOException ex) {
				jedisConfiguration = null;
				writeLog(ex.getMessage(), ex);
			}
		}
	}
	
	private static void writeLog(String message, Exception ex){
		if(ex instanceof JedisConnectionException){ //不能连接到Redis服务器
			log.error(message, ex);
		}else if(ex instanceof IOException){ //加载配置文件 jedis-config.properties 出错
			log.error(message, ex);
		}else if(ex instanceof NoSuchElementException){ //池中没有可用的Jedis对象
			log.error(message, ex);
		}else{
			log.error(message, ex);
		}
		ex.printStackTrace();
	}
	
}
