package org.eleme.qianggou.action.vo;

import java.io.Serializable;

import org.eleme.qianggou.biz.bo.QueryOrdersBo;

public class QueryOrdersVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
    private String items;
    private int total;
    
    public QueryOrdersVo() {
    	
    }
    public QueryOrdersVo(QueryOrdersBo queryOrdersVo) {
    	this.id = queryOrdersVo.getId();
    	this.total = queryOrdersVo.getTotal();
    	this.items = queryOrdersVo.getItems().toString();
    }
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setItems(String items) {
		this.items = items;
	}
	
	public String getItems() {
		return this.items;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
   
}
