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
		 * �ڵ���getWriter֮ǰδ���ñ���(�ȵ���setContentType����setCharacterEncoding�������ñ���) ,
		 * HttpServletResponse��᷵��һ����Ĭ�ϵı���(��ISO-8859-1)�����PrintWriterʵ���������ͻ�
		 * ����������롣�������ñ���ʱ�����ڵ���getWriter֮ǰ����,��Ȼ����Ч�ġ�
		 */
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (object != null) {
			// JSON�ڴ��ݹ���������ͨ�ַ�����ʽ���ݵģ������ƴ��һ��������
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
		 * �ڵ���getWriter֮ǰδ���ñ���(�ȵ���setContentType����setCharacterEncoding�������ñ���) ,
		 * HttpServletResponse��᷵��һ����Ĭ�ϵı���(��ISO-8859-1)�����PrintWriterʵ���������ͻ�
		 * ����������롣�������ñ���ʱ�����ڵ���getWriter֮ǰ����,��Ȼ����Ч�ġ�
		 */
		if (messageStatseEnum != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// JSON�ڴ��ݹ���������ͨ�ַ�����ʽ���ݵģ������ƴ��һ��������
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
