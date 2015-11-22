package org.eleme.qianggou.dal.dao.impl;

import java.util.List;

import org.eleme.qianggou.dal.dao.OrderDao;
import org.eleme.qianggou.dal.dao.common.impl.BaseDaoHibernate4;
import org.eleme.qianggou.dal.dom.OrderDo;

public class OrderDaoImpl  extends BaseDaoHibernate4<OrderDo> implements OrderDao {

	@Override
	public List<OrderDo> findOrder(long userId) {
		return find("select o from OrderDo as o where "
				+ "o.userId=?0" , userId);
	}

}
