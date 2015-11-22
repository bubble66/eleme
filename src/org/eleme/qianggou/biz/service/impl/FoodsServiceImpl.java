package org.eleme.qianggou.biz.service.impl;

import java.util.List;

import org.eleme.qianggou.biz.service.FoodsService;
import org.eleme.qianggou.dal.dao.FoodDao;
import org.eleme.qianggou.dal.dom.FoodDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("foodsService")
public class FoodsServiceImpl  implements FoodsService {
	
	@Autowired
	@Qualifier("foodDao")
	private FoodDao foodDao;
	
	@Override
	public List<FoodDo> getFoodsList() {
		return foodDao.findAll(FoodDo.class);
	}


}
