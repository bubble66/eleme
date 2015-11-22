package org.eleme.qianggou.dal.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * foodDo
 * 存放订单信息
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */

@Entity
@Table(name = "orders")
public class OrderDo implements Serializable {

	private static final long serialVersionUID = 3L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "item_id")
	private Integer itemId;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "count")
	private Integer count;

	// 无参数的构造器
	public OrderDo() {
	}

	// 初始化全部成员变量的构造器
	public OrderDo(Integer userId, Integer itemId, Integer price, Integer count) {
		this.userId = userId;
		this.itemId = itemId;
		this.price = price;
		this.count = count;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
