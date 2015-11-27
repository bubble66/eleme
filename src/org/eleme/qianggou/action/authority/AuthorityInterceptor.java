package org.eleme.qianggou.action.authority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.SendJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 11L;
	private static final String ACCESS_TOKEN_HEAD = "Access-Token";
	private static final String ACCESS_TOKEN = "access_token";
	private static final Logger log = LoggerFactory.getLogger(AuthorityInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		Jedis jedis = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = ServletActionContext.getRequest();
			String access_token_param = (String) request.getParameter(ACCESS_TOKEN);
			if (access_token_param == null) {
				access_token_param = request.getHeader(ACCESS_TOKEN_HEAD);
			}
			
			if (access_token_param != null) {
				jedis = RedisClient.getJedis();
				String username = jedis.get("userToken-"+access_token_param);
				RedisClient.closeJedis(jedis);
				//System.out.println("Author *************  "+username + " requst url : " +  request.getContextPath() + request.getRequestURI());
				if (username != null) {
					ctx.getSession().put("userName", username);
					return invocation.invoke();
				}
			}
		} catch (Exception e) {
			RedisClient.closeJedis(jedis);
			System.out.println("Author *************  "+e.toString());
			/*
			 * HttpServletResponse response =
			 * ServletActionContext.getResponse();
			 * SendJson.sendObjectByJson(ErrorEnum.UNAUTHORIZED, response,
			 * HttpServletResponse.SC_UNAUTHORIZED); 
			 */
			return null;
		} 
		HttpServletResponse response = ServletActionContext.getResponse();
		SendJson.sendObjectByJson(ErrorEnum.UNAUTHORIZED, response,
				HttpServletResponse.SC_UNAUTHORIZED);
		return null;
	}
}