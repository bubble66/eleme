package org.eleme.qianggou.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.OrdersBo;
import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.biz.param.OrdersQueryParam;
import org.eleme.qianggou.biz.service.OrdersService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OrdersAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final int CARTS_ID_LENGTH = 32;

	private String cart_id;

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;

	// �����û�����
	public String execute() throws Exception {
		/**
		 * Struts2�ж�GET/POST����
		 */
		String method = ServletActionContext.getRequest().getMethod();
		System.out.println(method);
		if (method.equals("POST")) {
			return post();
		} else {
			return get();
		}
	}

	private String post() throws Exception {
		// ����ActionContextʵ��
		ActionContext ctx = ActionContext.getContext();
		String userId = (String) ctx.getSession().get("userName");

		if (cart_id.length() == CARTS_ID_LENGTH) {
			// ����ҵ���߼������������¼����
			OrdersQueryParam param = new OrdersQueryParam();
			param.setUserId(userId);
			param.setCartId(cart_id);
			Object resultObject = ordersService.orders(param);
			if (resultObject != null && resultObject instanceof OrdersBo) {
				HttpServletResponse response = ServletActionContext
						.getResponse();
				SendJson.sendObjectByJson(resultObject, response,
						HttpServletResponse.SC_OK);
				return SUCCESS;
			} else if (resultObject != null
					&& resultObject instanceof ErrorEnum) {
				HttpServletResponse response = ServletActionContext
						.getResponse();
				SendJson.sendObjectByJson(MessageStatseEnum
						.getEnumByMessage((ErrorEnum) resultObject), response);
				return ERROR;
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		SendJson.sendObjectByJson(
				MessageStatseEnum.getEnumByMessage(ErrorEnum.CART_NOT_FOUND),
				response);
		return ERROR;
	}

	private String get() throws Exception {
		// ����ActionContextʵ��
		ActionContext ctx = ActionContext.getContext();
		String userId = (String) ctx.getSession().get("userName");

		// ����ҵ���߼������������¼����
		OrdersQueryParam param = new OrdersQueryParam();
		param.setUserId(userId);
		Object resultObject = ordersService.findOrders(param);
		if (resultObject != null && resultObject instanceof QueryOrdersBo) {
			HttpServletResponse response = ServletActionContext.getResponse();
			SendJson.sendObjectByJson(resultObject, response,
					HttpServletResponse.SC_OK);
			return SUCCESS;
		} else if (resultObject != null && resultObject instanceof ErrorEnum) {
			HttpServletResponse response = ServletActionContext.getResponse();
			SendJson.sendObjectByJson(MessageStatseEnum
					.getEnumByMessage((ErrorEnum) resultObject), response);
			return ERROR;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		SendJson.sendObjectByJson(
				MessageStatseEnum.getEnumByMessage(ErrorEnum.CART_NOT_FOUND),
				response);
		return ERROR;
	}
}
