package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.param.PatchCartsQueryParam;
import org.eleme.qianggou.common.enums.ErrorEnum;

public interface CartsService {
	//�������ﳵ������ɹ����򷵻ع��ﳵId�����򷵻ؿ�
	public String creatCarts(String userId);

	//�������ﳵ������ɹ����򷵻�null, else return ErrorEnum
	public ErrorEnum patchCarts(PatchCartsQueryParam param);
}
