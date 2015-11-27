package org.eleme.qianggou.action;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.param.PatchCartsQueryParam;
import org.eleme.qianggou.biz.service.CartsService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PatchCartsAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final String FOOD_ID = "food_id";
	
	private static final String COUNT = "count";
	
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
		String userName = (String) ctx.getSession().get("userName");
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		String cartId = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/")+1);
		
		// 调用业务逻辑方法来处理登录请求
		try {
			ServletInputStream inputStream = ServletActionContext.getRequest()
					.getInputStream();
			JSONObject json = SendJson.JsonStrTrim(inputStream);
			if(json == null) {
				SendJson.sendObjectByJson(ErrorEnum.REQUEST_EMPTY, ServletActionContext.getResponse(),
						HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			if (json.has(FOOD_ID))
				food_id = json.getInt(FOOD_ID);
			if (json.has(COUNT))
				count = json.getInt(COUNT);
		} catch (Exception e) {
			SendJson.sendObjectByJson(ErrorEnum.REQUEST_MALFORMED, ServletActionContext.getResponse(),
					HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		try {
			if (cartId.length() == CARTS_ID_LENGTH) {
				// 调用业务逻辑方法来处理登录请求
				PatchCartsQueryParam param = new PatchCartsQueryParam();
				param.setCartId(cartId);
				param.setUserName(userName);
				param.setCount(count);
				param.setFoodId(food_id);
				ErrorEnum errorEnum = cartsService.patchCarts(param);
				if (errorEnum == null) {
					HttpServletResponse response = ServletActionContext.getResponse();
					SendJson.sendObjectByJson(null, response,
							HttpServletResponse.SC_NO_CONTENT);
					return null;
				} else {
					
					SendJson.sendObjectByJson(MessageStatseEnum.getEnumByMessage(errorEnum), ServletActionContext.getResponse());
					return null;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SendJson.sendObjectByJson(MessageStatseEnum.getEnumByMessage(ErrorEnum.CART_NOT_FOUND), ServletActionContext.getResponse());
		return null;

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
