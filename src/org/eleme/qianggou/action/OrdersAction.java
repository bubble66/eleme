package org.eleme.qianggou.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.OrdersBo;
import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OrdersAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final int CARTS_ID_LENGTH = 32;

	private static final String CART_ID = "cart_id";

	private String cart_id;
	
	private String access_token;

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;

	// 处理用户请求
	public String execute() throws Exception {
		/**
		 * Struts2判断GET/POST请求
		 */
		String method = ServletActionContext.getRequest().getMethod();
		// System.out.println(method);
		if (method.equals("POST")) {
			return post();
		} else {
			return get();
		}
	}

	private String post() throws Exception {
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		String userName = (String) ctx.getSession().get("userName");
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			ServletInputStream inputStream = ServletActionContext.getRequest()
					.getInputStream();
			JSONObject json = SendJson.JsonStrTrim(inputStream);
			if (json == null) {
				SendJson.sendObjectByJson(ErrorEnum.REQUEST_EMPTY, response,
						HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			if (json.has(CART_ID))
				cart_id = json.getString(CART_ID);
		} catch (Exception e) {
			SendJson.sendObjectByJson(ErrorEnum.REQUEST_MALFORMED, response,
					HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		if (cart_id.length() == CARTS_ID_LENGTH) {
			// 调用业务逻辑方法来处理登录请求
			OrdersQueryParam param = new OrdersQueryParam();
			param.setUserName(userName);
			param.setCartId(cart_id);
			Object resultObject = ordersService.orders(param);
			if (resultObject != null && resultObject instanceof OrdersBo) {
				SendJson.sendObjectByJson(resultObject, response,
						HttpServletResponse.SC_OK);
				return null;
			} else if (resultObject != null
					&& resultObject instanceof ErrorEnum) {

				SendJson.sendObjectByJson(MessageStatseEnum
						.getEnumByMessage((ErrorEnum) resultObject), response);
				return null;
			}
		}
		SendJson.sendObjectByJson(
				MessageStatseEnum.getEnumByMessage(ErrorEnum.CART_NOT_FOUND),
				response);
		return null;
	}

	private String get() throws Exception {
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		String userName = (String) ctx.getSession().get("userName");

		// 调用业务逻辑方法来处理登录请求
		OrdersQueryParam param = new OrdersQueryParam();
		param.setUserName(userName);
		List<QueryOrdersBo> resultList = ordersService.findOrders(param);
		HttpServletResponse response = ServletActionContext.getResponse();
		if(resultList == null)
			resultList = new ArrayList<QueryOrdersBo>(0);
		SendJson.sendObjectByJson(resultList, response,
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
