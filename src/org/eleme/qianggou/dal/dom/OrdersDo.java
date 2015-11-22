package org.eleme.qianggou.dal.dom;

import java.io.Serializable;
import java.util.List;

/**
 * foodDo 存放订单信息
 * 
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */

public class OrdersDo implements Serializable {
	private static final long serialVersionUID = 3L;

	private String ordersId;

	private String userId;

	private List<OrderDo> itemList;
	
	private int total;


	// 无参数的构造器
	public OrdersDo() {
	}

	// 初始化全部成员变量的构造器
	public OrdersDo(String userId, List<OrderDo> itemList) {
		this.userId = userId;
		this.itemList = itemList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<OrderDo> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderDo> itemList) {
		this.itemList = itemList;
	}
}
