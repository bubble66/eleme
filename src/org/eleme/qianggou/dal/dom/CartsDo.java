package org.eleme.qianggou.dal.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CartsDo
 * ��Ź��ﳵ��Ϣ
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */

public class CartsDo  implements Serializable {

	private static final long serialVersionUID = 4L;
	
	public static final int MAX_COUNT_FOOD = 3;

	//���ﳵId
	private String userId;
	
	//��ǰ���ﳵ�к��е���Ʒ��
	private Integer count = 0;

	//��Ʒ�б�
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
	
	//�����ﳵ������ʳ�����ɹ�����true��ʧ�ܷ���false
	public boolean addFoodList(CartsFoodDo food) {
		int count_ = food.getCount();
		if(count_ > MAX_COUNT_FOOD || this.count + count_ > MAX_COUNT_FOOD) 
			return false;
		this.count += count_;
		this.foodList.add(food);
		return true;
	}
}
