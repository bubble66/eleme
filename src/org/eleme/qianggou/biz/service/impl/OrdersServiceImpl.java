package org.eleme.qianggou.biz.service.impl;

import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.Byte2Object;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.eleme.qianggou.dal.dom.CartsDo;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Override
	public Object orders(OrdersQueryParam param) {
		Jedis jedis = RedisClient.getJedis();
		String cartId = param.getCartId();
		byte[] bytes = jedis.get(cartId.getBytes());
		if(bytes == null) 
			return ErrorEnum.CART_NOT_FOUND;
		CartsDo cartsDo = (CartsDo) Byte2Object.ByteToObject(bytes);
		String userId = cartsDo.getUserId();
		if(!userId.equals(param.getUserId()))
			return ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART;
		String keyOrders = userId +"-"+ cartId + "-"+ "Orders";
		if(jedis.exists(keyOrders))
			return ErrorEnum.ORDER_OUT_OF_LIMIT;
		
		String ordersId = UUIDGenerator.getUUID();
		jedis.set(keyOrders, ordersId);
		return ordersId;
	}

}
