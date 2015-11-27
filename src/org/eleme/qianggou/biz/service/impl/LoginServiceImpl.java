package org.eleme.qianggou.biz.service.impl;

import java.util.List;

import org.eleme.qianggou.biz.bo.LoginBo;
import org.eleme.qianggou.biz.service.LoginService;
import org.eleme.qianggou.dal.dao.UserDao;
import org.eleme.qianggou.dal.dom.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	public LoginBo validLogin(String userName, String password) {
		UserDo userDo = new UserDo(null, userName, password);
		List<UserDo> userList = userDao.findUser(userDo);
		if (userList != null && userList.size() > 0) {
			LoginBo loginBo = new LoginBo();
			loginBo.setUser_id(userList.get(0).getId());
			loginBo.setUsername(userName);

			return loginBo;
		} else
			return null;
	}
	
	public UserDo validLoginDo(String userName, String password) {
		UserDo userDo = new UserDo(null, userName, password);
		List<UserDo> userList = userDao.findUser(userDo);
		if (userList != null && userList.size() > 0) {

			return userList.get(0);
		} else
			return null;
	}

}
