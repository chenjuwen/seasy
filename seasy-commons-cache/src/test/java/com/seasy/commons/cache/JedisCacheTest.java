package com.seasy.commons.cache;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class JedisCacheTest {
	ICache cache = null;
	
	@Before
	public void before(){
		cache = new RedisCache();
	}
	
	@Test
	public void testUsername(){
		cache.delete("username");
		
		Assert.assertNull(cache.get("username"));
		
		Assert.assertTrue(cache.add("username", "123", 1));
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertNull(cache.get("username"));
		
		Assert.assertTrue(cache.add("username", "123"));
		Assert.assertEquals("123", cache.get("username"));
		
		cache.append("username", "456");
		Assert.assertEquals("123456", cache.get("username"));
		
		Assert.assertTrue(cache.exists("username"));
	}
	
	@Test
	public void testCount(){
		cache.delete("count1", "count2");
		
		Assert.assertEquals(1, cache.incr("count1"));
		Assert.assertEquals(0, cache.decr("count1"));

		Assert.assertNull(cache.getCounter("count2"));
		Assert.assertTrue(cache.storeCounter("count2", 100, 1));
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertNull(cache.getCounter("count2"));

		Assert.assertTrue(cache.storeCounter("count2", 100));
		Assert.assertEquals(100L, cache.getCounter("count2").longValue());
	}
	
}
