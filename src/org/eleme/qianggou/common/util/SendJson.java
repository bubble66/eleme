package org.eleme.qianggou.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.eleme.qianggou.common.enums.MessageStatseEnum;
import org.json.JSONObject;

public class SendJson {

	public static void sendObjectByJson(Object object, HttpServletResponse response, int status) throws IOException {
		/*
		 * �ڵ���getWriter֮ǰδ���ñ���(�ȵ���setContentType����setCharacterEncoding�������ñ���)
		 * ,
		 * HttpServletResponse��᷵��һ����Ĭ�ϵı���(��ISO-8859-1)�����PrintWriterʵ���������ͻ�
		 * ����������롣�������ñ���ʱ�����ڵ���getWriter֮ǰ����,��Ȼ����Ч�ġ�
		 */
		if(object != null) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// JSON�ڴ��ݹ���������ͨ�ַ�����ʽ���ݵģ������ƴ��һ��������
	        String jsonString = JSONObject.wrap(object).toString();
		    out.println(jsonString);
			out.flush();
			out.close();
		}
		if(status != 0)
			response.setStatus(status);

	}
	
	public static void sendObjectByJson(MessageStatseEnum messageStatseEnum, HttpServletResponse response) throws IOException {
		/*
		 * �ڵ���getWriter֮ǰδ���ñ���(�ȵ���setContentType����setCharacterEncoding�������ñ���)
		 * ,
		 * HttpServletResponse��᷵��һ����Ĭ�ϵı���(��ISO-8859-1)�����PrintWriterʵ���������ͻ�
		 * ����������롣�������ñ���ʱ�����ڵ���getWriter֮ǰ����,��Ȼ����Ч�ġ�
		 */
		if(messageStatseEnum != null) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// JSON�ڴ��ݹ���������ͨ�ַ�����ʽ���ݵģ������ƴ��һ��������
			Object message = messageStatseEnum.getMessage();
			if( message != null) {
		        String jsonString = JSONObject.wrap(message).toString();
			    out.println(jsonString);
				out.flush();
				out.close();
			}

	        int status = messageStatseEnum.getCode();
	        response.setStatus(status);

		}
	}
}
