package org.eleme.qianggou.dal.dom;

import java.io.Serializable;

/**
 * foodDo
 * 存放订单信息
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */

public class OrderDo implements Serializable {

	private static final long serialVersionUID = 6L;

	private Integer itemId;
	
	private Integer price;
	
	private Integer count;

	// 无参数的构造器
	public OrderDo() {
	}

	// 初始化全部成员变量的构造器
	public OrderDo(Integer itemId, Integer price, Integer count) {
		this.itemId = itemId;
		this.price = price;
		this.count = count;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
