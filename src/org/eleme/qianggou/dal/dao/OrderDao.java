package org.eleme.qianggou.dal.dao;

import java.util.List;

import org.eleme.qianggou.dal.dao.common.BaseDao;
import org.eleme.qianggou.dal.dom.OrdersDo;

public interface OrderDao  extends BaseDao<OrdersDo> {
	public List<OrdersDo> findOrder(long userId);
	//��������
	public void creatOrders(OrdersDo orderDo);
}
