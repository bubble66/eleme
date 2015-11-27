package org.eleme.qianggou.dal.dom;

import java.io.Serializable;

//购物车中方的食品数
public class CartsFoodDo implements Serializable {

	private static final long serialVersionUID = 4L;

	private int id;

	private int price;

	private int count;

	// 无参数的构造器
	public CartsFoodDo() {
	}

	// 初始化全部成员变量的构造器
	public CartsFoodDo(int id, int price, int count) {
		this.setId(id);
		this.price = price;
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(CartsFoodDo item) {
		if(this.id == item.getId())
			return true;
		return false;	
	}
}
