package com.troila.cloud.autoconfigure.qrcode.generator;

import java.awt.image.BufferedImage;

import com.google.zxing.NotFoundException;
import com.troila.cloud.autoconfigure.qrcode.core.QrCodeConfig;
import com.troila.cloud.autoconfigure.qrcode.core.pipline.QrPipline;

public interface QrCodeGenerator {
	
	BufferedImage encode(String url);
	
	BufferedImage encode(String url,QrCodeConfig config);
	
	@SuppressWarnings("rawtypes")
	void encodeWithPip(String url,QrPipline pip);
	
	String decode(BufferedImage image) throws NotFoundException;
}
