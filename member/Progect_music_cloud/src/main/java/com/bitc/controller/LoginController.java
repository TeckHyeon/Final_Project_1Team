package com.bitc.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.dto.MusicReplyDto;
import com.bitc.dto.UsersDto;
import com.bitc.service.MusicService;

@Controller
public class LoginController {

	@Autowired
	MusicService musicService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		session.setAttribute("userPk", 14);
		
		return "/index";
	}
	
	// 회원 가입 페이지
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String regiser() throws Exception {
		
		return "/login/register";
	}
	
	// 회원 가입 Process
	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public String registerProcess(UsersDto memberDto) throws Exception {
		
		musicService.register(memberDto);
		return "redirect:/";
	}
	
	// 이메일 중복 확인
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object regiserCheck(UsersDto memberDto) throws Exception {

		int result = musicService.emailCheck(memberDto.getUserEmail());

		HashMap<String, Integer> data = new HashMap<>();
		data.put("count", result);
		
		return data;
	}


	// 회원 정보 페이지
	@RequestMapping(value = "/memberInfo", method = RequestMethod.GET)
	public ModelAndView memberInfo(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
			
		int userPk =(int) session.getAttribute("userPk");
		
		UsersDto data = musicService.selectMemberInfo(userPk);
	
		ModelAndView mv = new ModelAndView("/login/memberInfo");
		mv.addObject("data", data);
		
		return mv;
	}
	
	// 회원 정보 수정 Process
	@RequestMapping(value = "/memberInfo", method = RequestMethod.PUT)
	public String update(UsersDto memberDto) throws Exception { 

		musicService.update(memberDto);
		 
		return "/index";
	}
		
	// 회원 탈퇴 Process
	@RequestMapping(value = "/memberInfo/{userPk}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("userPk") int userPk) throws Exception { 
		musicService.delete(userPk);
		
		return "redirect:/";
	}
	
	// ID/PW 찾기 페이지
	@RequestMapping(value = "/findIdPw", method = RequestMethod.GET)
	public String findIdPw() throws Exception {
		return "/login/findIdPw";
	}
	
	// ID/PW Process
	@ResponseBody
	@RequestMapping(value = "/findIdPw", method=RequestMethod.POST)
	public Object findpwProcess(@RequestParam HashMap<String, Object> hashMap) throws Exception {

		return musicService.findpwProcess(hashMap);
	}
	
	// 마이 페이지
	@RequestMapping("/myPage")
	public Object myPage(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		int userPk = (int) session.getAttribute("userPk");
		
		ModelAndView mv = new ModelAndView("/login/myPage");
		
		List<MusicReplyDto> replyData = musicService.reply(userPk); 
		return mv;
	}
	
	
}
