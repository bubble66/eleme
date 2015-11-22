package org.eleme.qianggou.biz.bo;

import java.io.Serializable;

public class OrdersBo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	public OrdersBo() {
		
	}
	
	public OrdersBo(String id) {
		this.id = id;
	}
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
