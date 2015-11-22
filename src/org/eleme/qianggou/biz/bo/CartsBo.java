package org.eleme.qianggou.biz.bo;

import java.io.Serializable;

public class CartsBo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String cart_id;
    public CartsBo(String cart_id) {
    	this.cart_id = cart_id;
    }

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
}
