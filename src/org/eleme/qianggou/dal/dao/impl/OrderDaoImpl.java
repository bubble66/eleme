package org.eleme.qianggou.dal.dao.impl;

import java.util.List;

import org.eleme.qianggou.dal.dao.OrderDao;
import org.eleme.qianggou.dal.dao.common.impl.BaseDaoHibernate4;
import org.eleme.qianggou.dal.dom.OrdersDo;

public class OrderDaoImpl  extends BaseDaoHibernate4<OrdersDo> implements OrderDao {

	@Override
	public List<OrdersDo> findOrder(long userId) {
		return find("select o from OrdersDo as o where "
				+ "o.userId=?0" , userId);
	}

	@Override
	public void creatOrders(OrdersDo ordersDo) {
		save(ordersDo);	
	}

}
