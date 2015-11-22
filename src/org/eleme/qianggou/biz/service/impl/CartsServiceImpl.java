package org.eleme.qianggou.biz.service.impl;

import org.eleme.qianggou.biz.param.PatchCartsQueryParam;
import org.eleme.qianggou.biz.service.CartsService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.Byte2Object;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.eleme.qianggou.dal.dom.CartsDo;
import org.eleme.qianggou.dal.dom.CartsFoodDo;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("cartsService")
public class CartsServiceImpl implements CartsService{

	@Override
	public String creatCarts(String userId) {
		String cartsId = UUIDGenerator.getUUID();
		Jedis jedis = RedisClient.getJedis();
		CartsDo cartsDo = new CartsDo();
		cartsDo.setUserId(userId);
		jedis.set(cartsId.getBytes(), Byte2Object.ObjectToByte(cartsDo));
		return cartsId;
	}

	@Override
	public ErrorEnum patchCarts(PatchCartsQueryParam param) {
		Jedis jedis = RedisClient.getJedis();
		String cartId = param.getCartId();
		byte[] bytes = jedis.get(cartId.getBytes());
		if(bytes == null) 
			return ErrorEnum.CART_NOT_FOUND;
		CartsDo cartsDo = (CartsDo) Byte2Object.ByteToObject(bytes);
		String userId = cartsDo.getUserId();
		if(!userId.equals(param.getUserId()))
			return ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART;
		if(!jedis.exists(String.valueOf(param.getFoodId())))
			return ErrorEnum.FOOD_NOT_FOUND;
		if(cartsDo.getCount() + param.getCount() > CartsDo.MAX_COUNT_FOOD)
			return ErrorEnum.FOOD_OUT_OF_LIMIT;
		long count = 0L - param.getCount();
		if(jedis.hincrBy(String.valueOf(param.getFoodId()), "stock", count) < 0) {
			jedis.hincrBy(String.valueOf(param.getFoodId()), "stock", -count);
			return ErrorEnum.FOOD_OUT_OF_STOCK;
		}
		int price = Integer.parseInt(jedis.hget(String.valueOf(param.getFoodId()), "price"));
		CartsFoodDo foodDo = new CartsFoodDo();
		foodDo.setCount(param.getCount());
		foodDo.setId(param.getFoodId());
		foodDo.setPrice(price);
		if(cartsDo.addFoodList(foodDo))
			jedis.set(cartId.getBytes(), Byte2Object.ObjectToByte(cartsDo));
		return null;
	}

}
