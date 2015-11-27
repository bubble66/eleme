package org.eleme.qianggou.biz.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminQueryOrdersBo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
    private Long user_id;
    private  List<ItemBo> items = new ArrayList<ItemBo>();
    private long total;
    
	public String getId() {
		return id;
	}

	public List<ItemBo> getItems() {
		return items;
	}

	public void setItems(List<ItemBo> items) {
		this.items = items;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setItems(int food_id, int count) {
		items.add(new ItemBo(food_id, count));
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
