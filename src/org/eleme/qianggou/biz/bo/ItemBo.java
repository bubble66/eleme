package org.eleme.qianggou.biz.bo;

import java.io.Serializable;

public class ItemBo implements Serializable {
		private static final long serialVersionUID = 10L;

		private int food_id;

		private int count;
    	
    	public ItemBo() {
    		
    	}
    	public ItemBo(int food_id, int count) {
    		this.setFood_id(food_id);
    		this.setCount(count);
    	}
		public int getFood_id() {
			return food_id;
		}
		public void setFood_id(int food_id) {
			this.food_id = food_id;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
}
