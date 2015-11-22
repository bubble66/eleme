package org.eleme.qianggou.dal.dao;

import java.util.List;

import org.eleme.qianggou.dal.dao.common.BaseDao;
import org.eleme.qianggou.dal.dom.UserDo;

public interface UserDao extends BaseDao<UserDo> {

	public boolean hasTheUser(UserDo userDo);
	
	public List<UserDo> findAllUser();
	
	public List<UserDo> findUser(UserDo userDo);
}
