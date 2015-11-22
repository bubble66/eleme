package org.eleme.qianggou.biz.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryOrdersBo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
    private List<ItemBo> items = new ArrayList<ItemBo>(0);
    private int total;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setItems(int food_id, int count) {
		items.add(new ItemBo(food_id, count));
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
   
}
