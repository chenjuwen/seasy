package com.seasy.core;

import org.junit.Assert;
import org.junit.Test;

import com.seasy.core.util.PropertiesUtil;

public class PropertiesUtilTest {
	@Test
	public void test1(){
		String proxyEnabled = PropertiesUtil.getInstance().getProperty("proxy.enabled");
		Assert.assertNotNull(proxyEnabled);
	}
}
