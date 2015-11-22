package org.eleme.qianggou.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.dal.dao.FoodDao;
import org.eleme.qianggou.dal.dom.FoodDo;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import redis.clients.jedis.Jedis;

public class DaoListener implements ServletContextListener {
	/*
	 * @Autowired
	 * 
	 * @Qualifier("foodDao") private FoodDao foodDao;
	 */

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Jedis jedis = RedisClient.getJedis();
		jedis.flushDB();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		WebApplicationContext application = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
		// 获取Bean的实例。
		FoodDao foodDao = (FoodDao) application.getBean("foodDao");

		List<FoodDo> foodList = foodDao.findAll(FoodDo.class);
		if (foodList != null) {
			Jedis jedis = RedisClient.getJedis();
			jedis.flushDB();
			for (int i = 0; i < foodList.size(); i++) {
				FoodDo tmp = foodList.get(i);
				System.out.println("###############DaoListener");
				System.out.println(tmp.getId().toString() + "stock"
						+ tmp.getStock().toString());
				jedis.hset(tmp.getId().toString(), "stock", tmp.getStock()
						.toString());
				jedis.hset(tmp.getId().toString(), "price", tmp.getPrice()
						.toString());
			}
		}
	}
}
