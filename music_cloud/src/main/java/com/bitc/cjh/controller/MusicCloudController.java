package com.bitc.cjh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.cjh.dto.MusicCloudMusicDto;
import com.bitc.cjh.dto.MusicCloudUserDto;
import com.bitc.cjh.service.MusicCloudService;

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

		/* 로그인 체크 */
		HttpSession session = request.getSession();
		session.setAttribute("userEmail", user.getUserEmail());

		return "/layout/header";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPage(MusicCloudUserDto user, Model model) throws Exception {

		return "main";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchResultPage(@RequestParam("keyword") String keyword) throws Exception {
		ModelAndView mv = new ModelAndView("searchresult");
		List<MusicCloudMusicDto> musicList = musicservice.searchMusic(keyword);
		mv.addObject("keyword", keyword);
		mv.addObject("search", musicList);
		
		Stream<MusicCloudMusicDto> musicListStream = musicList.stream();
		musicListStream.forEach(s -> {
			int userPk = s.getUserPk();
			List<MusicCloudUserDto> users = new ArrayList<MusicCloudUserDto>();
			try {
				users = musicservice.userInfoByPk(userPk);
				mv.addObject("users",users);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchPage(MusicCloudUserDto user, @RequestParam("keyword") String keyword, Model model)
			throws Exception {
		List<MusicCloudMusicDto> musicList = musicservice.searchMusic(keyword);

		model.addAttribute("musicList", musicList);
		return "searchresult";
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
	public @ResponseBody String emailCheck(MusicCloudUserDto user, HttpServletRequest request,
			@RequestParam("userEmail") String userEmail, @RequestParam("userPw") String userPw) throws Exception {
		int count = musicservice.selectMemberInfoYn(userEmail, userPw);
		String str = "";

		if (count == 1) {
			str = "YES";
		} else {
			str = "NO";
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
