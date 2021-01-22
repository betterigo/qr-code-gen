package com.troila.cloud.autoconfigure.qrcode.core;

import com.troila.cloud.autoconfigure.qrcode.core.pipline.QrCodePipline;
import com.troila.cloud.autoconfigure.qrcode.core.pipline.QrPipline;

/**
 * 默认的是二维码生成器实现
 * @author haodonglei
 *
 */
public class DefaultQrCodeGenerator extends AbstractQrCodeGenerator{

	private QrCodeConfig qrCodeConfig;
	
	public DefaultQrCodeGenerator() {
		super();
	}

	public DefaultQrCodeGenerator(QrCodeConfig qrCodeConfig) {
		super();
		this.qrCodeConfig = qrCodeConfig;
	}



	@Override
	protected QrCodeConfig getQrCodeConfig() {
		return qrCodeConfig;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void encodeWithPip(String url, QrPipline pip) {
		if(QrCodePipline.DynamicLogoSettings.getByteSupplier() != null) {			
			qrCodeConfig.getQrCodeLogoSettings().setType(LogoType.BASE64);
			qrCodeConfig.getQrCodeLogoSettings().setLogoBase64(QrCodePipline.DynamicLogoSettings.getStringSupplier().get());
			((QrCodePipline)pip).setTarget(this.encode(url, qrCodeConfig));
		}
		else if(QrCodePipline.DynamicLogoSettings.getStringSupplier() != null) {
			qrCodeConfig.getQrCodeLogoSettings().setType(LogoType.BASE64);
			qrCodeConfig.getQrCodeLogoSettings().setLogoBase64(QrCodePipline.DynamicLogoSettings.getStringSupplier().get());
			((QrCodePipline)pip).setTarget(this.encode(url, qrCodeConfig));
		}else {			
			((QrCodePipline)pip).setTarget(this.encode(url));
		}
		pip.process();
	}

}
