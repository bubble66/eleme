package org.eleme.qianggou.action.authority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.SendJson;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class AuthorityInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 11L;

	public String intercept(ActionInvocation invocation)
		throws Exception
	{
		// 创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		// 获取HttpSession中的level属性
		String access_token = (String) ctx.getSession().get("access_token");
		HttpServletRequest request = ServletActionContext.getRequest();
		String access_token_param = (String) request.getParameter("access_token");
		System.out.print(access_token_param);
		if(access_token != null && access_token.equals(access_token_param))
			return invocation.invoke();
		else {
			HttpServletResponse response = ServletActionContext.getResponse();
			SendJson.sendObjectByJson(ErrorEnum.UNAUTHORIZED, 
					response, HttpServletResponse.SC_UNAUTHORIZED);
			return "error";
		}
	}
}