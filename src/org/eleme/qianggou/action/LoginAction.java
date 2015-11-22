package org.eleme.qianggou.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.LoginBo;
import org.eleme.qianggou.biz.service.LoginService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("loginService")
	private LoginService login;

	// 封装请求参数
	private transient String username;

	public void setUsername(String username) {
		this.username = username;
	}

	private transient String password;

	public void setPassword(String password) {
		this.password = password;
	}

	// 处理用户请求
	public String execute() throws Exception {
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		// 调用业务逻辑方法来处理登录请求
		LoginBo loginBo = login.validLogin(username, password);
		if (loginBo != null) {
			String uuid = UUIDGenerator.getUUID();
			ctx.getSession().put("access_token", uuid);
			ctx.getSession().put("userName", username);
			loginBo.setAccess_token(uuid);
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				SendJson.sendObjectByJson(loginBo, 
						response, HttpServletResponse.SC_OK);
			} catch (Exception e) {
				System.out.println(e);
			}

			return SUCCESS;
		}
		// 用户名和密码不匹配
		else {
			HttpServletResponse response = ServletActionContext.getResponse();
			SendJson.sendObjectByJson(ErrorEnum.USER_AUTH_FAIL, 
					response, HttpServletResponse.SC_FORBIDDEN);
			return ERROR;
		}
	}
}