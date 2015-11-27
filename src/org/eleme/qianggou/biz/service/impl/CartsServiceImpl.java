package org.eleme.qianggou.biz.service.impl;

import java.util.List;

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
public class CartsServiceImpl implements CartsService {

	@Override
	public String creatCarts(String userName) {
		String cartsId = UUIDGenerator.getUUID();
		CartsDo cartsDo = new CartsDo();
		cartsDo.setUserName(userName);
		Jedis jedis = RedisClient.getJedis();
		jedis.set(("cartsId-" + cartsId).getBytes(),
				Byte2Object.ObjectToByte(cartsDo));
		RedisClient.closeJedis(jedis);
		return cartsId;
	}

	@Override
	public ErrorEnum patchCarts(PatchCartsQueryParam param) {
		Jedis jedis = null;
		try {
			jedis = RedisClient.getJedis();
			String cartId = param.getCartId();
			byte[] bytes = jedis.get(("cartsId-" + cartId).getBytes());
			if (bytes == null) {
				return ErrorEnum.CART_NOT_FOUND;
			}
			CartsDo cartsDo = (CartsDo) Byte2Object.ByteToObject(bytes);
			String userName = cartsDo.getUserName();
			if (!userName.equals(param.getUserName())) {
				return ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART;
			}
			if (cartsDo.getCount() + param.getCount() > CartsDo.MAX_COUNT_FOOD) {
				return ErrorEnum.FOOD_OUT_OF_LIMIT;
			}
			List<String> foodInfoList = jedis.hmget(
					"FoodTable-" + param.getFoodId(), "stock", "price");
			if (foodInfoList == null || foodInfoList.get(0) == null
					|| foodInfoList.get(1) == null) {
				return ErrorEnum.FOOD_NOT_FOUND;
			}
			Integer stock = Integer.parseInt(foodInfoList.get(0));
			long count = param.getCount();
			if (stock - count < 0) {
				return ErrorEnum.FOOD_OUT_OF_STOCK;
			}
			int price = Integer.parseInt(foodInfoList.get(1));
			CartsFoodDo foodDo = new CartsFoodDo();
			foodDo.setCount(param.getCount());
			foodDo.setId(param.getFoodId());
			foodDo.setPrice(price);
			if (cartsDo.addFoodList(foodDo))
				jedis.set(("cartsId-" + cartId).getBytes(),
						Byte2Object.ObjectToByte(cartsDo));
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			RedisClient.closeJedis(jedis);
		}
		return ErrorEnum.CART_NOT_FOUND;
	}

}
