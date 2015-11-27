package org.eleme.qianggou.action.vo;

import java.io.Serializable;

import org.eleme.qianggou.biz.bo.AdminQueryOrdersBo;

public class AdminQueryOrdersVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
    private Long user_id;
    private  String items;
    private long total;
    
	public AdminQueryOrdersVo() {
	}
    
	public AdminQueryOrdersVo(AdminQueryOrdersBo adminQueryOrdersBo) {
		this.id = adminQueryOrdersBo.getId();
		this.total = adminQueryOrdersBo.getTotal();
		this.user_id = adminQueryOrdersBo.getUser_id();
		this.items = adminQueryOrdersBo.getItems().toString();
	}

	public String getId() {
		return id;
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

	public void setItems(String items) {
		this.items=items;
	}
	
	public String getItems() {
		return this.items;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
