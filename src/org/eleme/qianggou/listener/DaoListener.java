package org.eleme.qianggou.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.dal.dao.FoodDao;
import org.eleme.qianggou.dal.dao.UserDao;
import org.eleme.qianggou.dal.dom.FoodDo;
import org.eleme.qianggou.dal.dom.UserDo;
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
		RedisClient.closeJedis(jedis);
		RedisClient.destroyPool();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		WebApplicationContext application = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
		// 获取Bean的实例。
		FoodDao foodDao = (FoodDao) application.getBean("foodDao");
		Jedis jedis = RedisClient.getJedis();
		jedis.flushDB();
		List<FoodDo> foodList = foodDao.findAll(FoodDo.class);
		if (foodList != null) {
			for (int i = 0; i < foodList.size(); i++) {
				FoodDo tmp = foodList.get(i);
				// System.out.println("###############DaoListener");
				// System.out.println(tmp.getId().toString() + "stock"
				// + tmp.getStock().toString());

				Map<String, String> map = new HashMap<String, String>();
				map.put("id", tmp.getId().toString());
				map.put("price", tmp.getPrice().toString());
				map.put("stock", tmp.getStock().toString());
				jedis.hmset("FoodTable-" + tmp.getId(), map);
			}
		}

		UserDao userDao = (UserDao) application.getBean("userDao");
		List<UserDo> userList = userDao.findAllUser();
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				UserDo tmp = userList.get(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", tmp.getId().toString());
				map.put("username", tmp.getName());
				map.put("password", tmp.getPassword());
				jedis.hmset("userTable-" + tmp.getName(), map);
			}
		}

		RedisClient.closeJedis(jedis);
	}
}
