package com.bitc.cjh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		//apache의 Commons-io 및 Commons-fileupload를 사용하기 때문에 CommonsMultipartResolver를 통해서 MultipartResolver를 구현하고 스프링 프레임워크에 등록
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		
		//최대 업로드 가능 파일크기 설정, 기본단위는 byte 2^10(KB) 2^20(MB)
		commonsMultipartResolver.setMaxUploadSizePerFile(100 * 1024 * 1024);
		
		return commonsMultipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/audio/**")
		.addResourceLocations("file:///c:/workspace-spring-tool-suite-4-4.12.1.RELEASE/music_cloud/audio/");
	}
	

}
