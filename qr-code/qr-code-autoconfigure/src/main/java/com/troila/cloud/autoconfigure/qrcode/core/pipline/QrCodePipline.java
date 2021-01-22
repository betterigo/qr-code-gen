package com.troila.cloud.autoconfigure.qrcode.core.pipline;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class QrCodePipline<T> implements QrPipline<T>{

	private T target;
	
	private QrCodePipline<?> next;
	
	private Function<? super T, ?> codec;
	
	private Consumer<? super T> consumer;
	
	@SuppressWarnings("unchecked")
	public void setTarget(Object target) {
		this.target = (T) target;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> QrPipline<R> withCodeC(Function<? super T, ? extends R> codec) {
		this.codec = codec;
		this.next = new QrCodePipline<>();
		return (QrPipline<R>) next;
	}

	@Override
	public void consume(Consumer<? super T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void process() {
		if(this.codec!=null) {			
			Object result = this.codec.apply(target);
			this.next.setTarget(result);
			this.next.process();
		}
		if(this.consumer != null) {
			consumer.accept(target);
		}
	}

	public static QrPipline<BufferedImage> pipline() {
		return new QrCodePipline<>();
	}

	@Override
	public QrPipline<T> withLogoBase64(boolean condition, Supplier<String> base64LogoSupplier) {
		if(condition){
			DynamicLogoSettings.setStringSupplier(base64LogoSupplier);
		}
		return this;
	}

	@Override
	public QrPipline<T> withLogoBytes(boolean condition, Supplier<byte[]> bytesLogoSupplier) {
		if (condition) {
			DynamicLogoSettings.setByteSupplier(bytesLogoSupplier);
		}
		return this;
	}
	
	public static class DynamicLogoSettings{
		static ThreadLocal<Supplier<String>> stringSupplier = new ThreadLocal<>();
		static ThreadLocal<Supplier<byte[]>> byteSupplier = new ThreadLocal<>();
		public static Supplier<String> getStringSupplier(){
			return stringSupplier.get();
		}
		public static Supplier<byte[]> getByteSupplier(){
			return byteSupplier.get();
		}
		public static void setStringSupplier(Supplier<String> base64LogoSupplier){
			stringSupplier.set(base64LogoSupplier);
		}
		public static void setByteSupplier(Supplier<byte[]> bytesLogoSupplier){
			byteSupplier.set(bytesLogoSupplier);
		}
	}
}
