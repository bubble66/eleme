package org.eleme.qianggou.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.json.JSONObject;

public class SendJson {

	public static void sendObjectByJson(Object object,
			HttpServletResponse response, int status) throws IOException {
		/*
		 * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码) ,
		 * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
		 * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
		 */
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (object != null) {
			// JSON在传递过程中是普通字符串形式传递的，这里简单拼接一个做测试
			String jsonString = JSONObject.wrap(object).toString();
			out.println(jsonString);
		}
		if (status != 0)
			response.setStatus(status);
		out.flush();
		out.close();

	}

	public static void sendObjectByJson(MessageStatseEnum messageStatseEnum,
			HttpServletResponse response) throws IOException {
		/*
		 * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码) ,
		 * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
		 * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
		 */
		if (messageStatseEnum != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// JSON在传递过程中是普通字符串形式传递的，这里简单拼接一个做测试
			Object message = messageStatseEnum.getMessage();
			if (message != null) {
				String jsonString = JSONObject.wrap(message).toString();
				out.println(jsonString);
			}

			int status = messageStatseEnum.getCode();
			response.setStatus(status);
			out.flush();
			out.close();

		}
	}

	public static JSONObject JsonStrTrim(InputStream inputStream) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		if(sb.toString() == null || "".equals(sb.toString()))
			return null;
		JSONObject json = new JSONObject(sb.toString());
		return json;
	}
}
