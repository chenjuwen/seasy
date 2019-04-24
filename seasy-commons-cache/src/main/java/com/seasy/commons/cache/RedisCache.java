package com.seasy.commons.cache;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisCache implements ICache {
	private static Logger log = Logger.getLogger(RedisCache.class);
	public static final String OK = "OK";
	
	@Override
	public boolean set(String key, Object value) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			String result = jedis.set(key, String.valueOf(value));
			return OK.equalsIgnoreCase(result);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean set(String key, Object value, int expireSeconds) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			String result = jedis.set(key, String.valueOf(value));
			
			if(OK.equalsIgnoreCase(result)){
				jedis.expire(key, expireSeconds);
			}
			return OK.equalsIgnoreCase(result);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean add(String key, Object value) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			long result = jedis.setnx(key, String.valueOf(value));
			return result == 1;
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean add(String key, Object value, int expireSeconds) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			long result = jedis.setnx(key, String.valueOf(value));
			if(result == 1){
				jedis.expire(key, expireSeconds);
			}
			return result == 1;
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean delete(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.del(key) == 1;
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean delete(String... keys) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.del(keys) == 1;
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean append(String key, String value) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.append(key, value) == 1;
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public Object get(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.get(key);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return null;
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.exists(key);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.incr(key);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return 0;
	}

	@Override
	public long decr(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			return jedis.decr(key);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return 0;
	}

	@Override
	public boolean storeCounter(String key, long counter) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			String result = jedis.set(key, String.valueOf(counter));
			return OK.equalsIgnoreCase(result);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean storeCounter(String key, long counter, int expireSeconds) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			String result = jedis.set(key, String.valueOf(counter));
			if(OK.equalsIgnoreCase(result)){
				jedis.expire(key, expireSeconds);
			}
			return OK.equalsIgnoreCase(result);
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public Long getCounter(String key) {
		Jedis jedis = null;
		try{
			jedis = SeasyJedisPool.getResource();
			String result = jedis.get(key);
			if(result != null){
				return new Long(result);
			}
		}catch(Exception ex){
			writeLog(ex);
		}finally{
			SeasyJedisPool.returnResource(jedis);
		}
		return null;
	}
	
	private void writeLog(Exception ex){
		log.error("Redis Cache operation error", ex);
		ex.printStackTrace();
	}

}
