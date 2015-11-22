package org.eleme.qianggou.common.exception;

public class LoginException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public LoginException(){}
	public LoginException(String msg)
	{
		super(msg);
	}
}