package org.eleme.qianggou.dal.dao;

import java.util.List;

import org.eleme.qianggou.dal.dao.common.BaseDao;
import org.eleme.qianggou.dal.dom.FoodDo;

public interface FoodDao extends BaseDao<FoodDo> {

	List<FoodDo> getFoodList();

	List<FoodDo> getFood(FoodDo foodDo);

	void updateFood(FoodDo foodDo);
}
