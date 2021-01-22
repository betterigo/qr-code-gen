package com.troila.cloud.autoconfigure.qrcode.core;

public class QrCodeLogoSettings {
	
	private String logo;
	
	private byte[] logoBytes;
	
	private String logoBase64;
	
	private LogoType type;
	
	private int width = 60;
	
	private int height = 60;
	
	private boolean needCompress = true;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isNeedCompress() {
		return needCompress;
	}

	public void setNeedCompress(boolean needCompress) {
		this.needCompress = needCompress;
	}

	public LogoType getType() {
		return type;
	}

	public void setType(LogoType type) {
		this.type = type;
	}

	public byte[] getLogoBytes() {
		return logoBytes;
	}

	public void setLogoBytes(byte[] logoBytes) {
		this.logoBytes = logoBytes;
	}

	public String getLogoBase64() {
		return logoBase64;
	}

	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}
}
