package com.troila.cloud.autoconfigure.qrcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.troila.cloud.autoconfigure.qrcode.core.DefaultQrCodeGenerator;
import com.troila.cloud.autoconfigure.qrcode.core.QrCodeConfig;
import com.troila.cloud.autoconfigure.qrcode.core.QrCodeConfigurationAdapter;
import com.troila.cloud.autoconfigure.qrcode.generator.QrCodeGenerator;

/**
 * 二维码生成器 自动配置类
 * @author haodonglei
 *
 */
@Configuration
public class QrCodeAutoConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(QrCodeAutoConfiguration.class);
	
	@Bean
	@ConditionalOnMissingBean(QrCodeConfig.class)
	public QrCodeConfig qrCodeConfig() {
		logger.info("创建默认二维码设置选项...");
		return new QrCodeConfigurationAdapter();
	}
	
//	@ConditionalOnBean(QrCodeConfig.class)
//	@ConditionalOnMissingBean(QrCodeGenerator.class)
//	static class Inner2{
//		@Autowired
//		private QrCodeConfig config;
//		
//		@Bean
//		@ConditionalOnBean(QrCodeConfig.class)
//		@ConditionalOnMissingBean(QrCodeGenerator.class)
//		public QrCodeGenerator qrCodeGenerator() {
//			logger.info("创建默认二维码生成器...");
//			return new DefaultQrCodeGenerator(config);
//		}
//	}
	
	@Bean
	@ConditionalOnBean(QrCodeConfig.class)
	@ConditionalOnMissingBean(QrCodeGenerator.class)
	public QrCodeGenerator qrCodeGenerator(QrCodeConfig config) {
		logger.info("创建默认二维码生成器...");
		return new DefaultQrCodeGenerator(config);
	}
}
