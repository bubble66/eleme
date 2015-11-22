package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.param.OrdersQueryParam;

public interface OrdersService {

	public Object orders(OrdersQueryParam param);

	public Object findOrders(OrdersQueryParam param);

}
