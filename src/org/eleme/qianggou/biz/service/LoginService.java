package org.eleme.qianggou.biz.service;

import org.eleme.qianggou.biz.bo.LoginBo;

public interface LoginService {
	public LoginBo validLogin(String userName, String password);
}
