package org.eleme.qianggou.common.enums;

public enum ErrorEnum {
	UNAUTHORIZED("INVALID_ACCESS_TOKEN", "��Ч������"), 
	REQUEST_EMPTY("EMPTY_REQUEST", "������Ϊ��"), 
	REQUEST_MALFORMED("MALFORMED_JSON", "��ʽ����"),
	USER_AUTH_FAIL("USER_AUTH_FAIL", "�û������������"),
	CART_NOT_FOUND("CART_NOT_FOUND", "���Ӳ�����"),
	CART_EMPTY("CART_EMPTY", "����Ϊ��"),
	NOT_AUTHORIZED_TO_ACCESS_CART("NOT_AUTHORIZED_TO_ACCESS_CART", "��Ȩ�޷���ָ��������"),
	NOT_AUTHORIZED_TO_ACCESS_CART_ORDER("NOT_AUTHORIZED_TO_ACCESS_CART", "��Ȩ�޷���ָ��������"),
	FOOD_OUT_OF_STOCK("FOOD_OUT_OF_STOCK", "ʳ���治��"), 
	FOOD_OUT_OF_LIMIT("FOOD_OUT_OF_LIMIT", "������ʳ����������������"), 
	FOOD_NOT_FOUND("FOOD_NOT_FOUND", "ʳ�ﲻ����"), 
	ORDER_OUT_OF_LIMIT("ORDER_OUT_OF_LIMIT", "ÿ���û�ֻ����һ��"),
	SQL_ERROR("SQL_ERROR", "���ݿ����"),
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
