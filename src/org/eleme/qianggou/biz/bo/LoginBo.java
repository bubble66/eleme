package org.eleme.qianggou.biz.bo;

import java.io.Serializable;

public class LoginBo  implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private long user_id;
    
	private String username;
	
    private String access_token;
    
    
    public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
