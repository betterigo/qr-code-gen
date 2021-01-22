package com.troila.cloud.autoconfigure.qrcode.core.encoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ImageBase64converter {
	
	public static String image2Base64(byte[] imageBytes) {
		return Base64.getEncoder().encodeToString(imageBytes);
	}
	
	public static String image2Base64(BufferedImage image) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", bos);
		return image2Base64(bos.toByteArray());
	}

	public static byte[] base642ImageBytes(String imageBase64) {
		return Base64.getDecoder().decode(imageBase64);
	}
	
	public static InputStream base642InputSteam(String imageBase64) {
		byte[] imgByte = base642ImageBytes(imageBase64);
		return bytes2InputSteam(imgByte);
	}
	
	public static InputStream bytes2InputSteam(byte[] imageBytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
		return bis;
	}
}
