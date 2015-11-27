package org.eleme.qianggou.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.service.FoodsService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.SendJson;
import org.eleme.qianggou.dal.dom.FoodDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

public class FoodsAction  extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String access_token;
	
	@Autowired
	@Qualifier("foodsService")
	private FoodsService foodsService;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
	// 处理用户请求
		public String execute() throws Exception {
			// 调用业务逻辑方法来处理登录请求
			//List<FoodDo> foodsList = foodsService.getFoodsListByRedis();
			List<FoodDo> foodsList = foodsService.getFoodsList();
			if (foodsList != null) {
				HttpServletResponse response = ServletActionContext.getResponse();
				SendJson.sendObjectByJson(foodsList, 
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
