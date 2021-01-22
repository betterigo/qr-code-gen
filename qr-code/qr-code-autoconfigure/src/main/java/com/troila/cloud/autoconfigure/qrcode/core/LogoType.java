package com.troila.cloud.autoconfigure.qrcode.core;

/**
 * 二维码logo图片类型
 * @author haodonglei
 *
 */
public enum LogoType {
	
	FILE("FILE"),
	BYTE("BYTE"),
	BASE64("BASE64");
	
	private String value;

	private LogoType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
