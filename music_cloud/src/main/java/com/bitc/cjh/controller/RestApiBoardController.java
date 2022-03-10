package com.bitc.cjh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@Controller 어노테이션을 사용하면 클라이언트에게 데이터를 전송시 View Model을 함께 전송
//@ResponseBody 어노테이션을 사용하면 클라이언트에게 데이터만 전송함

//@RestController : @Controller와 @ResponseBody가 합쳐진 형태의 어노테이션, @ResponseBody 어노테이션을 사용하지 않아도 클라이언트에게 데이터만 전송
//Rest API 란 웹 브라우저가 아닌 다른 디바이스와 통신이 가능하도록 연결 창구를 만들어주는 것
//단순 웹 브라우저만 아니라 안드로이드 및 IOS 앱과 서버의 통신이 가능함
@RestController
public class RestApiBoardController {
	

	@RequestMapping(value="/")
	public ModelAndView openIndex() throws Exception{
		ModelAndView mv = new ModelAndView("/index");
		
		return mv;
	}

}
