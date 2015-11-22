package org.eleme.qianggou.dal.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CartsDo
 * 存放购物车信息
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */

public class CartsDo  implements Serializable {

	private static final long serialVersionUID = 4L;
	
	public static final int MAX_COUNT_FOOD = 3;

	//购物车Id
	private String userId;
	
	//当前购物车中含有的物品数
	private Integer count = 0;

	//物品列表
	private List<CartsFoodDo> foodList = new ArrayList<CartsFoodDo>(0);

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<CartsFoodDo> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<CartsFoodDo> foodList) {
		this.foodList = foodList;
	}
	
	//往购物车中增加食物，如果成功返回true，失败返回false
	public boolean addFoodList(CartsFoodDo food) {
		int count_ = food.getCount();
		if(count_ > MAX_COUNT_FOOD || this.count + count_ > MAX_COUNT_FOOD) 
			return false;
		this.count += count_;
		this.foodList.add(food);
		return true;
	}
}
