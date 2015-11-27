package org.eleme.qianggou.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.AdminQueryOrdersBo;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminOrdersAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final String ADMIN_ID = "root";

	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	private String access_token;
	
	// 处理用户请求
	public String execute() throws Exception {
		/**
		 * Struts2判断GET/POST请求
		 */
		return get();
	}

	private String get() throws Exception {
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		String userId = (String) ctx.getSession().get("userName");
		if (!userId.equals(ADMIN_ID)) {
			HttpServletResponse response = ServletActionContext.getResponse();
			SendJson.sendObjectByJson(ErrorEnum.UNAUTHORIZED, response,
					HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}

		// 调用业务逻辑方法来处理登录请求
		List<AdminQueryOrdersBo> ordersList = ordersService.listAllOrders();
		HttpServletResponse response = ServletActionContext.getResponse();
		if(ordersList == null)
			ordersList = new ArrayList<AdminQueryOrdersBo>(0);
		SendJson.sendObjectByJson(ordersList, response,
				HttpServletResponse.SC_OK);
		return null;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}
