package com.team1.musiccloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.team1.musiccloud.service.AudioService;


//@Controller 어노테이션을 사용하면 클라이언트에게 데이터를 전송시 View Model을 함께 전송
//@ResponseBody 어노테이션을 사용하면 클라이언트에게 데이터만 전송함

//@RestController : @Controller와 @ResponseBody가 합쳐진 형태의 어노테이션, @ResponseBody 어노테이션을 사용하지 않아도 클라이언트에게 데이터만 전송
//Rest API 란 웹 브라우저가 아닌 다른 디바이스와 통신이 가능하도록 연결 창구를 만들어주는 것
//단순 웹 브라우저만 아니라 안드로이드 및 IOS 앱과 서버의 통신이 가능함

@RestController
public class MainController {
	
	@Autowired
	private AudioService audioService;

	@RequestMapping(value="/upload", method = RequestMethod.GET)
	public ModelAndView openIndex() throws Exception{
		ModelAndView mv = new ModelAndView("/file_IO/upload_music");
		
		return mv;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public Map<String, Object> insertProductRegistration(MultipartHttpServletRequest multiFiles) throws Exception {		
		
		audioService.insertAudio(multiFiles);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("SUCCESS", true);
		
		return result;
	}
	
	/*
	@RequestMapping(value="/admin/product/registration", method=RequestMethod.POST)
	public Map<String, Object> insertProductRegistration(
			@RequestPart(value="optionKey") List<Map<String, Object>> optionList,
			@RequestPart(value="productKey") Map<String, Object> productItem,
			@RequestPart(value="file", required=false) List<MultipartFile> multiFiles) throws Exception {		
		
		productService.insertProductItem(optionList, productItem, multiFiles);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("SUCCESS", true);
		
		return result;
	}
	*/
}
