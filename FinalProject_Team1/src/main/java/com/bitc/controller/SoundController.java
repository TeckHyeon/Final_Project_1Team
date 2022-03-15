package com.bitc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.dto.SoundDto;
import com.bitc.service.SoundService;

@Controller
public class SoundController {

	@Autowired
	private SoundService soundService;

	@RequestMapping("/")
	public String index() throws Exception {
		return "index";
	}

	@RequestMapping("profilelist.do")
	public ModelAndView addrList() throws Exception {
		ModelAndView mv = new ModelAndView("/sound/userList");

		List<SoundDto> userList = soundService.selectUserProfile();
		mv.addObject("userList", userList);

		return mv;
	}
	
	@RequestMapping("test.do")
	public ModelAndView crazyList() throws Exception {
		ModelAndView mv = new ModelAndView("/sound/test");
		
		int userPk = 1;
		List<SoundDto> playList = soundService.selectUserPlaylist(userPk);
		mv.addObject("playList", playList);

		return mv;
	}

	@RequestMapping("profile.do")
	public ModelAndView userProfile(@RequestParam("userPk") int userPk) throws Exception {

		ModelAndView mv = new ModelAndView("/sound/userprofile");

		SoundDto sound = soundService.userProfile(userPk);
		List<SoundDto> playList = soundService.selectUserPlaylist(userPk);
		mv.addObject("sound", sound);
		mv.addObject("playList", playList);

		return mv;
	}
	
	// 테스트

}
