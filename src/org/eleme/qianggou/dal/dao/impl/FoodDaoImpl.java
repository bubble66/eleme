package org.eleme.qianggou.dal.dao.impl;

import java.util.List;

import org.eleme.qianggou.dal.dao.FoodDao;
import org.eleme.qianggou.dal.dao.common.impl.BaseDaoHibernate4;
import org.eleme.qianggou.dal.dom.FoodDo;

public class FoodDaoImpl extends BaseDaoHibernate4<FoodDo> implements FoodDao {

	@Override
	public List<FoodDo> getFoodList() {
		return findAll(FoodDo.class);
	}

	@Override
	public List<FoodDo> getFood(FoodDo foodDo) {
		return find("select f from FoodDo as f where "
				+ "f=?0" , foodDo);
	}

	@Override
	public void updateFood(FoodDo foodDo) {
		update(foodDo);
	}

	

}
