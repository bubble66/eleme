package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.common.enums.ErrorEnum;

public interface OrdersService {

	public Object orders(OrdersQueryParam param);

}
