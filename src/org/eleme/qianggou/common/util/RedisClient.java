package org.eleme.qianggou.common.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	private static JedisPool pool;
    protected static Logger log = LoggerFactory.getLogger(RedisClient.class);  
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException(
					"[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		
		
		
		config.setMaxActive(Integer.valueOf(bundle
				.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle
				.getString("redis.pool.maxIdle")));
		config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle
				.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle
				.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"),
				Integer.valueOf(bundle.getString("redis.port")));
	}
	public static Jedis getJedis() {
		try {
			return pool.getResource();
		} catch(Exception e) {
			log.error("JedisPool:getJedis error!!!" + e.toString());
		}
		return null;
	}
	
	public static void closeJedis(Jedis resource) {
		try {
			pool.returnResource(resource);
		} catch(Exception e) {
			System.out.println("JedisPool:closeJedis error!!!" + e.toString());
		}
	}
	public static void destroyPool() {
		try {
			pool.destroy();
		} catch(Exception e) {
			log.error("JedisPool:destroyPool error!!!" + e.toString());
		}
	}

	
}
