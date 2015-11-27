package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.bo.LoginBo;
import org.eleme.qianggou.dal.dom.UserDo;

public interface LoginService {
	public LoginBo validLogin(String userName, String password);

	public UserDo validLoginDo(String username, String password);
}
