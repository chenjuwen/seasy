package com.seasy.commons.cache;

public interface ICache {
	/**
	 * 缓存对象
	 * @param key
	 * @param value
	 * @return 
	 * 		true成功，false失败
	 */
	public boolean set(String key, Object value);
	
	/**
	 * 缓存对象（含超时时间）
	 * @param key
	 * @param value
	 * @param expireSeconds 多少秒后过期
	 * @return
	 * 		true成功，false失败
	 */
	public boolean set(String key, Object value, int expireSeconds);
	
	/**
	 * 添加对象到缓存
	 * @param key
	 * @param value
	 * @return 
	 * 		true成功，false失败
	 */
	public boolean add(String key, Object value);
	
	/**
	 * 添加对象到缓存（含超时时间）
	 * @param key
	 * @param value
	 * @param expireSeconds 多少秒后过期
	 * @return
	 * 		true成功，false失败
	 */
	public boolean add(String key, Object value, int expireSeconds);
	
	/**
	 * 删除缓存对象
	 * @param key
	 * @return
	 * 		true成功，false失败
	 */
	public boolean delete(String key);
	
	/**
	 * 批量删除缓存对象
	 * @param key
	 * @return
	 * 		true成功，false失败
	 */
	public boolean delete(String... keys);
	
	/**
	 * 在缓存值后追加字符串
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean append(String key, String value);
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 检查缓存对象是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key);
	
	/**
	 * 计数器自增1
	 * @param key
	 * @return
	 */
	public long incr(String key);
	
	/**
	 * 计数器自减1
	 * @param key
	 * @return
	 */
	public long decr(String key);
	
	/**
	 * 保存一个计数器到缓存
	 * @param key
	 * @param counter
	 * @return
	 */
	public boolean storeCounter(String key, long counter);
	
	/**
	 * 保存一个计数器到缓存（含过期时间）
	 * @param key
	 * @param counter
	 * @param expireSeconds 多少秒后过期
	 * @return
	 */
	public boolean storeCounter(String key, long counter, int expireSeconds);
	
	/**
	 * 获取计数器
	 * @param key
	 * @return
	 */
	public Long getCounter(String key);
	
}
