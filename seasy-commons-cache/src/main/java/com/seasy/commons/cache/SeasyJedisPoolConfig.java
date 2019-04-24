package com.seasy.commons.cache;

public interface SeasyJedisPoolConfig {
	public static final String HOST = "jedis.host";
	public static final String PORT = "jedis.port";
	public static final String TIMEOUT = "jedis.timeout";
	public static final String PASSWORD = "jedis.password";
	
	public static final String MAXTOTAL = "jedis.pool.maxTotal";
	public static final String MINIDLE = "jedis.pool.minIdle";
	public static final String MAXIDLE = "jedis.pool.maxIdle";
	public static final String MAX_WAIT_MILLIS = "jedis.pool.maxWaitMillis";
	public static final String TEST_ON_BORROW = "jedis.pool.testOnBorrow";
	public static final String TEST_ON_RETURN = "jedis.pool.testOnReturn";
	
	public static final int DEFAULT_PORT = 6379;
	public static final int DEFAULT_TIMEOUT = 3000;
	
	public static final int DEFAULT_MAXTOTAL = 8;
	public static final int DEFAULT_MINIDLE = 0;
	public static final int DEFAULT_MAXIDLE = 8;
	public static final long DEFAULT_MAX_WAIT_MILLIS = -1L;
	public static final boolean DEFAULT_TEST_ON_BORROW = false;
	public static final boolean DEFAULT_TEST_ON_RETURN = false;
	
}
