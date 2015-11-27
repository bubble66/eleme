package org.eleme.qianggou.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.CartsBo;
import org.eleme.qianggou.biz.service.CartsService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CartsAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String access_token;
	
	@Autowired
	@Qualifier("cartsService")
	private CartsService cartsService;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
	// 处理用户请求
		public String execute() throws Exception {
			// 创建ActionContext实例
			ActionContext ctx = ActionContext.getContext();
			// 获取HttpSession中的level属性
			String userName = (String) ctx.getSession().get("userName");
			// 调用业务逻辑方法来处理登录请求
			String cartsId = cartsService.creatCarts(userName);
			if (cartsId != null) {
				CartsBo cartsBo = new CartsBo(cartsId);
				HttpServletResponse response = ServletActionContext.getResponse();
				SendJson.sendObjectByJson(cartsBo, 
						response, HttpServletResponse.SC_OK);
				return null;
			}
			else {
				HttpServletResponse response = ServletActionContext.getResponse();
				SendJson.sendObjectByJson(ErrorEnum.SQL_ERROR, 
						response, HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		}
}
