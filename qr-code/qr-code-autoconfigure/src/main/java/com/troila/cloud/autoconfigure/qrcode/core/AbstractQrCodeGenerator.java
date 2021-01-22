package com.troila.cloud.autoconfigure.qrcode.core;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.troila.cloud.autoconfigure.qrcode.core.encoder.ImageBase64converter;
import com.troila.cloud.autoconfigure.qrcode.generator.QrCodeGenerator;

public abstract class AbstractQrCodeGenerator implements QrCodeGenerator {

	private static final String CHARSET = "utf-8";

	protected abstract QrCodeConfig getQrCodeConfig();

	public BufferedImage encode(String url) {
		return encode(url, getQrCodeConfig());
	}

	public BufferedImage encode(String url, QrCodeConfig config) {
		QrCodeLogoSettings logoSettings = config.getQrCodeLogoSettings();
		QrCodeSettings qrCodeSettings = config.getQrCodeSettings();
		try {
			return generatorQrCode(url, qrCodeSettings.getSize(), logoSettings.getLogo(), config.getQrCodeLogoSettings(), logoSettings.getWidth(),
					logoSettings.getHeight(), logoSettings.isNeedCompress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BufferedImage generatorQrCode(String content, int size, String logoPath, QrCodeLogoSettings type, int logoWidth, int logoHeight,
			boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (type == null) {
			return image;
		}

		insertImage(image, size, logoPath, type, logoWidth, logoHeight, needCompress);
		return image;
	}

	private void insertImage(BufferedImage source, int size, String logoPath, QrCodeLogoSettings qrCodeLogoSettings, int logoWidth, int logoHeight,
			boolean needCompress) throws Exception {
		Image src = null;
		//先判断logo的文件类型
		if(qrCodeLogoSettings.getType() == null && qrCodeLogoSettings.getType() == LogoType.FILE) {			
			File file = new File(qrCodeLogoSettings.getLogo());
			if (!file.exists()) {
				throw new Exception("logo file not found.");
			}
			src = ImageIO.read(file);
		}
		if(qrCodeLogoSettings.getType() == LogoType.BYTE) {
			src = ImageIO.read(ImageBase64converter.bytes2InputSteam(qrCodeLogoSettings.getLogoBytes()));
		}
		if(qrCodeLogoSettings.getType() == LogoType.BASE64) {
			src = ImageIO.read(ImageBase64converter.base642InputSteam(qrCodeLogoSettings.getLogoBase64()));
		}
		if(src == null) {
			return;
		}
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > logoWidth) {
				width = logoWidth;
			}
			if (height > logoHeight) {
				height = logoHeight;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (size - width) / 2;
		int y = (size - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	public String decode(BufferedImage image) throws NotFoundException {
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}
}
