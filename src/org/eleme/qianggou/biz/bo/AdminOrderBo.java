package org.eleme.qianggou.biz.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderBo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
    private long user_id;
    private List<Item> items = new ArrayList<Item>(0);
    private long total;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public void setItems(long food_id, long count) {
		items.add(new Item(food_id, count));
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
    
    private class Item implements Serializable {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		private long food_id;
    	@SuppressWarnings("unused")
		private long count;
    	
    	public Item(long food_id, long count) {
    		this.food_id = food_id;
    		this.count = count;
    	}
    }
}
