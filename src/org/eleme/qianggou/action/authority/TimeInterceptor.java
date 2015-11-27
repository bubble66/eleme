package org.eleme.qianggou.action.authority;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TimeInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 11L;

	//private static final Logger log = LoggerFactory.getLogger(TimeInterceptor.class); 

	public String intercept(ActionInvocation invocation) throws Exception {
		long start =System.currentTimeMillis(); //��ȡ����ʱ��
		String result =  invocation.invoke();
		long end=System.currentTimeMillis(); //��ȡ����ʱ��
		System.out.println(ServletActionContext.getRequest().getRequestURI() + "��������ʱ�䣺 "+(end-start)+"ms");
		return result;
	}
}