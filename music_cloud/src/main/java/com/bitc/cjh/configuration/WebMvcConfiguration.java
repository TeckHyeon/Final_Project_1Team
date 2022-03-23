package com.bitc.cjh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitc.cjh.inerceptor.LoginCheckInterceptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				
				new LoginCheckInterceptor()).addPathPatterns("/detail");
			
				/*
				new LoginCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/loginFail")
				.excludePathPatterns("/login").excludePathPatterns("/loginCheck").excludePathPatterns("/")
				.excludePathPatterns("/signin").excludePathPatterns("/emailCheck").excludePathPatterns("/main")
				.excludePathPatterns("/header")
				.excludePathPatterns("/css/**", "/js/**", "/image/**", "/audio/**", "/node_modules/**")
				.excludePathPatterns("/detail").excludePathPatterns("upload/**").excludePathPatterns("/search")
				.excludePathPatterns("/index");
				*/
	}
	
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
		registry.addResourceHandler("/upload/**")
		.addResourceLocations("file:///c:/workspace-spring-tool-suite-4-4.12.1.RELEASE/music_cloud/audio/");
		
		registry.addResourceHandler("/audio/**")
		//.addResourceLocations("file:///c:/workspace-spring-tool-suite-4-4.12.1.RELEASE/music_cloud/audio/");
		.addResourceLocations("file:///C:/Users/CJH/git/Final_Project_1Team/music_cloud/audio/");
		
	}
	

}
