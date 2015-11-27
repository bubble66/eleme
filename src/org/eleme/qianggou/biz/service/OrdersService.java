package org.eleme.qianggou.biz.service;

import java.util.List;

import org.eleme.qianggou.biz.bo.AdminQueryOrdersBo;
import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.biz.param.OrdersQueryParam;

public interface OrdersService {

	public Object orders(OrdersQueryParam param);

	public List<QueryOrdersBo> findOrders(OrdersQueryParam param);

	public List<AdminQueryOrdersBo> listAllOrders();

}
