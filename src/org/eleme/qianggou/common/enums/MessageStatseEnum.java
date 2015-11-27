package org.eleme.qianggou.common.enums;

import javax.servlet.http.HttpServletResponse;

public enum MessageStatseEnum {
	CART_NOT_FOUND(ErrorEnum.CART_NOT_FOUND, HttpServletResponse.SC_NOT_FOUND),
	
	FOOD_NOT_FOUND(ErrorEnum.FOOD_NOT_FOUND, HttpServletResponse.SC_NOT_FOUND),
	
	CART_EMPTY(ErrorEnum.CART_EMPTY, HttpServletResponse.SC_FORBIDDEN),
	
	FOOD_OUT_OF_LIMIT(ErrorEnum.FOOD_OUT_OF_LIMIT, HttpServletResponse.SC_FORBIDDEN),
	
	FOOD_OUT_OF_STOCK(ErrorEnum.FOOD_OUT_OF_STOCK, HttpServletResponse.SC_FORBIDDEN),
	
	NOT_AUTHORIZED_TO_ACCESS_CART(ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART, HttpServletResponse.SC_UNAUTHORIZED),
	
	NOT_AUTHORIZED_TO_ACCESS_CART_ORDER(ErrorEnum.NOT_AUTHORIZED_TO_ACCESS_CART_ORDER, HttpServletResponse.SC_FORBIDDEN),
	
	ORDER_OUT_OF_LIMIT(ErrorEnum.ORDER_OUT_OF_LIMIT, HttpServletResponse.SC_FORBIDDEN),
	
	REQUEST_EMPTY(ErrorEnum.REQUEST_EMPTY, HttpServletResponse.SC_BAD_REQUEST),
	
	REQUEST_MALFORMED(ErrorEnum.REQUEST_MALFORMED, HttpServletResponse.SC_BAD_REQUEST),
	
	UNAUTHORIZED(ErrorEnum.UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED),
	
	USER_AUTH_FAIL(ErrorEnum.USER_AUTH_FAIL, HttpServletResponse.SC_FORBIDDEN),
	
	SQL_ERROR(ErrorEnum.SQL_ERROR, HttpServletResponse.SC_FORBIDDEN),
	PATCH_FOOD(null, HttpServletResponse.SC_NO_CONTENT)
	;
	private int code;
	private Object message;

	MessageStatseEnum(Object message, int code) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public Object getMessage() {
		return message;
	}
	
	public static MessageStatseEnum getEnumByMessage(Object message) {
		if(message == null)
			return PATCH_FOOD;
		for(MessageStatseEnum index : MessageStatseEnum.values()) {
			if(index.getMessage() == null)
				continue;
			if(index.getMessage().equals(message))
				return index;
		}
		return null;
	}
}
