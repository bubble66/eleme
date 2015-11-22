package org.eleme.qianggou.dal.dao.impl;

import java.util.List;

import org.eleme.qianggou.dal.dao.UserDao;
import org.eleme.qianggou.dal.dao.common.impl.BaseDaoHibernate4;
import org.eleme.qianggou.dal.dom.UserDo;

public class UserDaoImpl extends BaseDaoHibernate4<UserDo> implements UserDao {

	@Override
	public boolean hasTheUser(UserDo userDo) {
		List<UserDo> user = find("select u from UserDo as u where "
				+ "u.name=?0 and u.password=?1" , userDo.getName(), userDo.getPassword());
		if(user != null && user.size() > 0)
			return true;
		return false;
	}

	@Override
	public List<UserDo> findAllUser() {
		return find("select u from UserDo as u");
	}

	@Override
	public List<UserDo> findUser(UserDo userDo) {
		return find("select u from UserDo as u where "
				+ "u.name=?0 and u.password=?1" , userDo.getName(), userDo.getPassword());
	}

}
