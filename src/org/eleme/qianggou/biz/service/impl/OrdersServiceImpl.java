package org.eleme.qianggou.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.eleme.qianggou.biz.bo.OrdersBo;
import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.Byte2Object;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.eleme.qianggou.dal.dao.OrderDao;
import org.eleme.qianggou.dal.dom.CartsDo;
import org.eleme.qianggou.dal.dom.CartsFoodDo;
import org.eleme.qianggou.dal.dom.OrderDo;
import org.eleme.qianggou.dal.dom.OrdersDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	@Qualifier("orderDao")
	private OrderDao orderDao;

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
		String keyOrders = userId + "-"+ "Orders";
		if(jedis.exists(keyOrders.getBytes()))
			return ErrorEnum.ORDER_OUT_OF_LIMIT;
		OrdersDo ordersDo = new OrdersDo();
		List<CartsFoodDo> foodList = cartsDo.getFoodList();
		List<OrderDo> orderList = new ArrayList<OrderDo>();
		int total = 0;
		for(int i = 0; i < foodList.size();i++) {
			CartsFoodDo cartsFoodDo = foodList.get(i);
			OrderDo orderDo = new OrderDo();
			orderDo.setCount(cartsFoodDo.getCount());
			orderDo.setItemId(cartsFoodDo.getId());
			orderDo.setPrice(cartsFoodDo.getPrice());
			orderList.add(orderDo);
			total += cartsFoodDo.getCount()*cartsFoodDo.getPrice();
		}
		String ordersId = UUIDGenerator.getUUID();
		ordersDo.setUserId(userId);
		ordersDo.setOrdersId(ordersId);
		ordersDo.setItemList(orderList);
		ordersDo.setTotal(total);
		//orderDao.creatOrders(ordersDo);
		jedis.set(keyOrders.getBytes(), Byte2Object.ObjectToByte(ordersDo));
		return new OrdersBo(ordersId);
	}

	@Override
	public Object findOrders(OrdersQueryParam param) {
		String keyOrders = param.getUserId() + "-"+ "Orders";
		Jedis jedis = RedisClient.getJedis();
		if(!jedis.exists(keyOrders.getBytes()))
			return ErrorEnum.ORDER_OUT_OF_LIMIT;
		byte[] bytes = jedis.get(keyOrders.getBytes());
		if(bytes == null) 
			return ErrorEnum.CART_NOT_FOUND;
		OrdersDo ordersDo = (OrdersDo) Byte2Object.ByteToObject(bytes);
		
		QueryOrdersBo queryOrdersBo = new QueryOrdersBo();
		queryOrdersBo.setId(ordersDo.getOrdersId());
		queryOrdersBo.setTotal(ordersDo.getTotal());
		List<OrderDo> itemList = ordersDo.getItemList();
		
		for(int i = 0; i < itemList.size();i++) {
			OrderDo item = itemList.get(i);
			queryOrdersBo.setItems(item.getItemId(), item.getCount());
		}
		return queryOrdersBo;
	}
}
