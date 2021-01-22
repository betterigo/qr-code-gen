package com.troila.cloud.autoconfigure.qrcode.core;

public class QrCodeConfigurationAdapter implements QrCodeConfig {

	private QrCodeLogoSettings qrCodeLogoSettings;

	private QrCodeSettings qrCodeSettings;

	public QrCodeLogoSettings getQrCodeLogoSettings() {
		if (qrCodeLogoSettings == null) {
			qrCodeLogoSettings = new QrCodeLogoSettings();
			configure(qrCodeLogoSettings);
		}
		return qrCodeLogoSettings;
	}

	public QrCodeSettings getQrCodeSettings() {
		if (qrCodeSettings == null) {
			qrCodeSettings = new QrCodeSettings();
			configure(qrCodeSettings);
		}
		return qrCodeSettings;
	}

	protected void configure(QrCodeLogoSettings qrCodeLogoSettings) {

	}

	protected void configure(QrCodeSettings qrCodeSettings) {

	}
}
