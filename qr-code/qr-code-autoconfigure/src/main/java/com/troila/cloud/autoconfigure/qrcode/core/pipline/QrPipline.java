package com.troila.cloud.autoconfigure.qrcode.core.pipline;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 管道命令
 * @author haodonglei
 *
 */
public interface QrPipline<T> {

	 <R> QrPipline<R> withCodeC(Function<? super T,? extends R> codec);
	 
	 QrPipline<T> withLogoBase64(boolean condition, Supplier<String> base64LogoSupplier);
	 
	 QrPipline<T> withLogoBytes(boolean condition, Supplier<byte[]> bytesLogoSupplier);
	 
	 void consume(Consumer<? super T> consumer);
	 
	 void process();
	
}
