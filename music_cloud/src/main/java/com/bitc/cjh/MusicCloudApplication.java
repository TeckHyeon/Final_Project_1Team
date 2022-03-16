package com.bitc.cjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;


//스프링부트의 기본 기능 중 자동 구성(@EnableAutoConfiguration)이 존재함
//apache의 Commons-fileupload를 사용하기 위해서 설정을 했기 때문에 해당 부분의 자동 설정을 해제해야 함
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class MusicCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicCloudApplication.class, args);
	}

}
