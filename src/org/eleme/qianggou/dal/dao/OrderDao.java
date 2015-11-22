package org.eleme.qianggou.dal.dao;

import java.util.List;

import org.eleme.qianggou.dal.dao.common.BaseDao;
import org.eleme.qianggou.dal.dom.OrderDo;

public interface OrderDao  extends BaseDao<OrderDo> {
	public List<OrderDo> findOrder(long userId);
}
