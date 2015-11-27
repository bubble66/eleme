package org.eleme.qianggou.common.enums;

public enum ErrorEnum {
	UNAUTHORIZED("INVALID_ACCESS_TOKEN", "无效的令牌"), 
	REQUEST_EMPTY("EMPTY_REQUEST", "请求体为空"), 
	REQUEST_MALFORMED("MALFORMED_JSON", "格式错误"),
	USER_AUTH_FAIL("USER_AUTH_FAIL", "用户名或密码错误"),
	CART_NOT_FOUND("CART_NOT_FOUND", "篮子不存在"),
	CART_EMPTY("CART_EMPTY", "篮子为空"),
	NOT_AUTHORIZED_TO_ACCESS_CART("NOT_AUTHORIZED_TO_ACCESS_CART", "无权限访问指定的篮子"),
	NOT_AUTHORIZED_TO_ACCESS_CART_ORDER("NOT_AUTHORIZED_TO_ACCESS_CART", "无权限访问指定的篮子"),
	FOOD_OUT_OF_STOCK("FOOD_OUT_OF_STOCK", "食物库存不足"), 
	FOOD_OUT_OF_LIMIT("FOOD_OUT_OF_LIMIT", "篮子中食物数量超过了三个"), 
	FOOD_NOT_FOUND("FOOD_NOT_FOUND", "食物不存在"), 
	ORDER_OUT_OF_LIMIT("ORDER_OUT_OF_LIMIT", "每个用户只能下一单"),
	SQL_ERROR("SQL_ERROR", "数据库出错"),
	;
	private String code;
	private String message;

	ErrorEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return code + ":" + message;
	}

}
