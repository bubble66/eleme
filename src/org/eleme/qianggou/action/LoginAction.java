package org.eleme.qianggou.action;

import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eleme.qianggou.biz.bo.LoginBo;
import org.eleme.qianggou.biz.service.LoginService;
import org.eleme.qianggou.common.enums.ErrorEnum;
import org.eleme.qianggou.common.util.RedisClient;
import org.eleme.qianggou.common.util.SendJson;
import org.eleme.qianggou.common.util.UUIDGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import redis.clients.jedis.Jedis;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

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
		HttpServletResponse response = ServletActionContext.getResponse();
		// 调用业务逻辑方法来处理登录请求
		try {
			ServletInputStream inputStream = ServletActionContext.getRequest()
					.getInputStream();
			JSONObject json = SendJson.JsonStrTrim(inputStream);
			if (json == null) {
				SendJson.sendObjectByJson(ErrorEnum.REQUEST_EMPTY, response,
						HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			if (json.has(USERNAME))
				username = json.getString(USERNAME);
			if (json.has(PASSWORD))
				password = json.getString(PASSWORD);
		} catch (Exception e) {
			SendJson.sendObjectByJson(ErrorEnum.REQUEST_MALFORMED, response,
					HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Jedis jedis = RedisClient.getJedis();
		List<String> loginInfoList = jedis.hmget("userTable-" + username,
				"userId", "username", "password", "uuid");
		try {
			String userIdDb = loginInfoList.get(0);
			String usernameDb = loginInfoList.get(1);
			String passwordDb = loginInfoList.get(2);
			String uuidDb = loginInfoList.get(3);
			if (userIdDb != null && usernameDb != null && passwordDb != null) {
				if (uuidDb == null) {
					uuidDb = UUIDGenerator.getUUID();
					jedis.set("userToken-" + uuidDb, username);
					jedis.hset("userTable-" + username, "uuid", uuidDb);
				}
				RedisClient.closeJedis(jedis);
				if (usernameDb.equals(username) && passwordDb.equals(password)) {

					// 如果之前已经登录，则不产生新的token
					LoginBo loginBo = new LoginBo();
					loginBo.setAccess_token(uuidDb);
					loginBo.setUsername(usernameDb);
					loginBo.setUser_id(Long.parseLong(userIdDb));

					SendJson.sendObjectByJson(loginBo, response,
							HttpServletResponse.SC_OK);

					return null;
				}
			}
		} catch (Exception e) {
			// 用户名和密码不匹配
			SendJson.sendObjectByJson(ErrorEnum.USER_AUTH_FAIL, response,
					HttpServletResponse.SC_FORBIDDEN);
			e.printStackTrace();
			return null;
		}

		/*
		 * if (loginInfoList != null && loginInfoList.size() >= 4) { String
		 * userIdDb = loginInfoList.get(0); String usernameDb =
		 * loginInfoList.get(1); String passwordDb = loginInfoList.get(2);
		 * String uuidDb = loginInfoList.get(3); if (userIdDb == null ||
		 * usernameDb == null || passwordDb == null) { UserDo loginDo =
		 * login.validLoginDo(username, password); if(loginDo == null) { //
		 * 用户名和密码不匹配 SendJson.sendObjectByJson(ErrorEnum.USER_AUTH_FAIL,
		 * response, HttpServletResponse.SC_FORBIDDEN); return ERROR; } String
		 * uuid = UUIDGenerator.getUUID(); userIdDb =
		 * loginDo.getId().toString(); usernameDb = loginDo.getName();
		 * passwordDb = loginDo.getPassword(); uuidDb = uuid;
		 * 
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("userId", userIdDb); map.put("username", usernameDb);
		 * map.put("password", passwordDb); map.put("uuid", uuidDb);
		 * 
		 * jedis.set("userToken-" + uuid, username); jedis.hmset("userTable-" +
		 * loginDo.getName(), map); RedisClient.closeJedis(jedis); } if
		 * (usernameDb.equals(username) && passwordDb.equals(password)) {
		 * 
		 * // 如果之前已经登录，则不产生新的token LoginBo loginBo = new LoginBo();
		 * loginBo.setAccess_token(uuidDb); loginBo.setUsername(usernameDb);
		 * loginBo.setUser_id(Long.parseLong(userIdDb));
		 * 
		 * try { SendJson.sendObjectByJson(loginBo, response,
		 * HttpServletResponse.SC_OK); } catch (Exception e) {
		 * SendJson.sendObjectByJson(ErrorEnum.REQUEST_MALFORMED, response,
		 * HttpServletResponse.SC_BAD_REQUEST); return ERROR; }
		 * 
		 * return SUCCESS; } }
		 */

		// 用户名和密码不匹配
		SendJson.sendObjectByJson(ErrorEnum.USER_AUTH_FAIL, response,
				HttpServletResponse.SC_FORBIDDEN);
		return null;

	}
}