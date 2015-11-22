package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.param.PatchCartsQueryParam;
import org.eleme.qianggou.common.enums.ErrorEnum;

public interface CartsService {
	//创建购物车，如果成功，则返回购物车Id，否则返回空
	public String creatCarts(String userId);

	//创建购物车，如果成功，则返回null, else return ErrorEnum
	public ErrorEnum patchCarts(PatchCartsQueryParam param);
}
