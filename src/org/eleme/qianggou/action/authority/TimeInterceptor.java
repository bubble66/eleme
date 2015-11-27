package org.eleme.qianggou.action.authority;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TimeInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 11L;

	//private static final Logger log = LoggerFactory.getLogger(TimeInterceptor.class); 

	public String intercept(ActionInvocation invocation) throws Exception {
		long start =System.currentTimeMillis(); //获取结束时间
		String result =  invocation.invoke();
		long end=System.currentTimeMillis(); //获取结束时间
		System.out.println(ServletActionContext.getRequest().getRequestURI() + "程序运行时间： "+(end-start)+"ms");
		return result;
	}
}