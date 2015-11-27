package org.eleme.qianggou.biz.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eleme.qianggou.biz.service.FoodsService;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.dal.dao.FoodDao;
import org.eleme.qianggou.dal.dom.FoodDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("foodsService")
public class FoodsServiceImpl implements FoodsService {

	@Autowired
	@Qualifier("foodDao")
	private FoodDao foodDao;

	@Override
	public List<FoodDo> getFoodsList() {
		return foodDao.findAll(FoodDo.class);
	}

	public List<FoodDo> getFoodsListByRedis() {
		Jedis jedis = RedisClient.getJedis();
		Iterator<String> iter =jedis.keys("FoodTable-*").iterator();
		List<FoodDo> foodDoList = new ArrayList<FoodDo>();
		while (iter.hasNext()) {	
			try {
				String key = iter.next();
				List<String> foodInfo = jedis.hmget(key, "id","stock","price");
				if(foodInfo != null && foodInfo.size() == 3 && foodInfo.get(0) != null && foodInfo.get(1) != null && foodInfo.get(2) != null) {
					FoodDo tmp = new FoodDo(Integer.valueOf(foodInfo.get(0)), Integer.valueOf(foodInfo.get(1)), Integer.valueOf(foodInfo.get(2)));
					foodDoList.add(tmp);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RedisClient.closeJedis(jedis);		
		return foodDoList;
	}
}
