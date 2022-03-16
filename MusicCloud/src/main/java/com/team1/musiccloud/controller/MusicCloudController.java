package com.team1.musiccloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team1.musiccloud.dto.MusicCloudUserDto;
import com.team1.musiccloud.service.MusicCloudService;

@Controller
public class MusicCloudController {
	
	@Autowired
	private MusicCloudService musicservice;

	@RequestMapping("/")
	public String index() throws Exception {
		return "index";
	}
	
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(MusicCloudUserDto user, HttpServletRequest request, Model model) throws Exception {
		
		HttpSession session = request.getSession();
		session.setAttribute("userEmail", user.getUserEmail());
		return "/layout/header";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPage(MusicCloudUserDto user, Model model) throws Exception {

		return "main";
	}
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detailPage() throws Exception {
		return "detail";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(MusicCloudUserDto user) throws Exception {

		return "/login";
	}
	
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public @ResponseBody String emailCheck(MusicCloudUserDto user, HttpServletRequest request, @RequestParam("userEmail") String userEmail, @RequestParam("userPw") String userPw) throws Exception {
		int count = musicservice.selectMemberInfoYn(userEmail, userPw);
		String str = "";
		
		if (count == 1) {
			str = "YES";
		} else {
			str= "NO";
		}
		return str;
	}
	
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(MusicCloudUserDto user, HttpServletRequest request) throws Exception {

		int count = musicservice.selectMemberInfoYn(user.getUserEmail(), user.getUserPw());

		if (count == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("userEmail", user.getUserEmail());
			session.setAttribute("userName", user.getUserName());
			session.setMaxInactiveInterval(1800);
			
		} else {

			System.out.println("실패");
		}
		return "redirect:/main";
	}

	@RequestMapping(value = "/loginOK", method = RequestMethod.GET)
	public String loginOK(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		if (session.getAttribute("userEmail") != null) {
			return "/main";
		} else {
			return "/loginFail";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("userEmail");
		session.invalidate();

		return "redirect:/main";
	}

	@RequestMapping(value = "/loginFail", method = RequestMethod.GET)
	public String loginFail() throws Exception {
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() throws Exception {

		return "/signin";
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String createUser(MusicCloudUserDto User) throws Exception {
		musicservice.signinUser(User);

		return "redirect:/main";
	}
}
