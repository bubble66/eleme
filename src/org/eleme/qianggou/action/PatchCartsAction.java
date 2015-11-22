package org.eleme.qianggou.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.param.PatchCartsQueryParam;
import org.eleme.qianggou.biz.service.CartsService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PatchCartsAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final int CARTS_ID_LENGTH = 32;

	private String access_token;
	private int food_id;
	private int count;

	@Autowired
	@Qualifier("cartsService")
	private CartsService cartsService;

	// 处理用户请求
	public String execute() throws Exception {
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		// 获取HttpSession中的level属性
		String userId = (String) ctx.getSession().get("userName");
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		String cartId = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/")+1);
		if (cartId.length() == CARTS_ID_LENGTH) {
			// 调用业务逻辑方法来处理登录请求
			PatchCartsQueryParam param = new PatchCartsQueryParam();
			param.setCartId(cartId);
			param.setUserId(userId);
			param.setCount(count);
			param.setFoodId(food_id);
			ErrorEnum errorEnum = cartsService.patchCarts(param);
			if (errorEnum == null) {
				HttpServletResponse response = ServletActionContext
						.getResponse();
				SendJson.sendObjectByJson(null, response,
						HttpServletResponse.SC_NO_CONTENT);
				return SUCCESS;
			} else {
				HttpServletResponse response = ServletActionContext.getResponse();
				SendJson.sendObjectByJson(MessageStatseEnum.getEnumByMessage(errorEnum), response);
				return ERROR;
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		SendJson.sendObjectByJson(MessageStatseEnum.getEnumByMessage(ErrorEnum.SQL_ERROR), response);
		return ERROR;

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}
