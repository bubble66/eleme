package org.eleme.qianggou.biz.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eleme.qianggou.biz.bo.AdminQueryOrdersBo;
import org.eleme.qianggou.biz.bo.OrdersBo;
import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.Byte2Object;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.eleme.qianggou.dal.dom.CartsDo;
import org.eleme.qianggou.dal.dom.CartsFoodDo;
import org.eleme.qianggou.dal.dom.OrderDo;
import org.eleme.qianggou.dal.dom.OrdersDo;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

/*	@Autowired
	@Qualifier("orderDao")
	private OrderDao orderDao;*/

	@Override
	public Object orders(OrdersQueryParam param) {
		Jedis jedis = RedisClient.getJedis();
		String cartId = param.getCartId();
		//System.out.println("orders####################"+cartId);
		byte[] bytes = jedis.get(("cartsId-"+cartId).getBytes());
		if(bytes == null) {
			RedisClient.closeJedis(jedis);
			return ErrorEnum.CART_NOT_FOUND;
		}
		//System.out.println("orders####################"+bytes.toString());
		CartsDo cartsDo = (CartsDo) Byte2Object.ByteToObject(bytes);
		String userName = cartsDo.getUserName();
		if(!userName.equals(param.getUserName())) {
			RedisClient.closeJedis(jedis);
			return ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART;
		}
		String keyOrders = "orders-" + userName;
		if(jedis.exists(keyOrders.getBytes())) {
			RedisClient.closeJedis(jedis);
			return ErrorEnum.ORDER_OUT_OF_LIMIT;
		}
		
		OrdersDo ordersDo = new OrdersDo();
		List<CartsFoodDo> foodList = cartsDo.getFoodList();
		if(foodList == null || foodList.size() <= 0) {
			RedisClient.closeJedis(jedis);
			return ErrorEnum.FOOD_OUT_OF_STOCK;
		}
		List<OrderDo> orderList = new ArrayList<OrderDo>();
		List<IdAndCount> successId = new ArrayList<IdAndCount>(0);
		
		int total = 0;
		for(int i = 0; i < foodList.size();i++) {
			CartsFoodDo cartsFoodDo = foodList.get(i);
			
			int id = cartsFoodDo.getId();
			int count = cartsFoodDo.getCount();
			successId.add(new IdAndCount(id, count));
			//如果有一個貨物不足，則全部取消
			if(jedis.hincrBy("FoodTable-"+String.valueOf(id), "stock", -count) < 0) {
				for(int k = 0; k<successId.size(); k++ ) {
					jedis.hincrBy("FoodTable-"+String.valueOf(successId.get(k).id), "stock", successId.get(k).count);
				}
				RedisClient.closeJedis(jedis);
				return ErrorEnum.FOOD_OUT_OF_STOCK;
			}
			OrderDo orderDo = new OrderDo();
			orderDo.setCount(count);
			orderDo.setItemId(id);
			orderDo.setPrice(cartsFoodDo.getPrice());
			orderList.add(orderDo);
			total += cartsFoodDo.getCount()*cartsFoodDo.getPrice();
		}
		String ordersId = UUIDGenerator.getUUID();
		Long userId = Long.parseLong(jedis.hget("userTable-"+ userName, "userId"));
		ordersDo.setUserId(userId);
		ordersDo.setOrdersId(ordersId);
		ordersDo.setItemList(orderList);
		ordersDo.setTotal(total);
		//orderDao.creatOrders(ordersDo);
		jedis.set(keyOrders.getBytes(), Byte2Object.ObjectToByte(ordersDo));
		RedisClient.closeJedis(jedis);
		return new OrdersBo(ordersId);
	}

	private class IdAndCount {
		public int id;
		public int count;
		IdAndCount(int id, int count) {
			this.id = id;
			this.count = count;
		}
	}
	@Override
	public List<QueryOrdersBo> findOrders(OrdersQueryParam param) {
		String keyOrders = "orders-" + param.getUserName();
		Jedis jedis = RedisClient.getJedis();
		byte[] bytes = jedis.get(keyOrders.getBytes());
		RedisClient.closeJedis(jedis);
		if(bytes == null) 
			return null;
		OrdersDo ordersDo = (OrdersDo) Byte2Object.ByteToObject(bytes);
		List<QueryOrdersBo> queryOrdersBoList = new ArrayList<QueryOrdersBo>();
		QueryOrdersBo queryOrdersBo = new QueryOrdersBo();
		queryOrdersBo.setId(ordersDo.getOrdersId());
		queryOrdersBo.setTotal(ordersDo.getTotal());
		List<OrderDo> itemList = ordersDo.getItemList();
		
		for(int i = 0; i < itemList.size();i++) {
			OrderDo item = itemList.get(i);
			queryOrdersBo.setItems(item.getItemId(), item.getCount());
		}
		queryOrdersBoList.add(queryOrdersBo);
		return queryOrdersBoList;
	}

	@Override
	public List<AdminQueryOrdersBo> listAllOrders() {
		Jedis jedis = RedisClient.getJedis();
		Set<byte[]> keys = jedis.keys("orders-*".getBytes());
		if(keys == null || keys.size() <= 0) {
			RedisClient.closeJedis(jedis);
			return null;
		}
	    Iterator<byte[]> it=keys.iterator() ;
	    List<AdminQueryOrdersBo> ordersList = new ArrayList<AdminQueryOrdersBo>();
        while(it.hasNext()){   
        	byte[] key = it.next();   
			/*
			 * if(!jedis.exists(key)) continue;
			 */
    		byte[] bytes = jedis.get(key);
    		if(bytes == null) 
    			continue;
    		OrdersDo ordersDo = (OrdersDo) Byte2Object.ByteToObject(bytes);
    		
    		AdminQueryOrdersBo queryOrdersBo = new AdminQueryOrdersBo();
    		queryOrdersBo.setId(ordersDo.getOrdersId());
    		queryOrdersBo.setTotal(ordersDo.getTotal());
    		queryOrdersBo.setUser_id(ordersDo.getUserId());
    		List<OrderDo> itemList = ordersDo.getItemList();
    		
    		for(int i = 0; i < itemList.size();i++) {
    			OrderDo item = itemList.get(i);
    			queryOrdersBo.setItems(item.getItemId(), item.getCount());
    		}
    		ordersList.add(queryOrdersBo);
        }
		RedisClient.closeJedis(jedis);
		return ordersList;
	}
}
