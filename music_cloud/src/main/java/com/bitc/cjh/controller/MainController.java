package com.bitc.cjh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.UserDto;
import com.bitc.cjh.dto.UserPlaylistDto;
import com.bitc.cjh.service.IOService;
import com.bitc.cjh.service.MemberService;
import com.bitc.cjh.service.MusicCloudService;


//@Controller 어노테이션을 사용하면 클라이언트에게 데이터를 전송시 View Model을 함께 전송
//@ResponseBody 어노테이션을 사용하면 클라이언트에게 데이터만 전송함

//@RestController : @Controller와 @ResponseBody가 합쳐진 형태의 어노테이션, @ResponseBody 어노테이션을 사용하지 않아도 클라이언트에게 데이터만 전송
//Rest API 란 웹 브라우저가 아닌 다른 디바이스와 통신이 가능하도록 연결 창구를 만들어주는 것
//단순 웹 브라우저만 아니라 안드로이드 및 IOS 앱과 서버의 통신이 가능함

@Controller
public class MainController {
	
	@Autowired
	private IOService ioService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MusicCloudService mcService;
	
	
	/* ----- (강도현) ----- */
	@RequestMapping("/")
	public String index() throws Exception {
		return "index";
	}

	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(UserDto user, HttpServletRequest request, Model model) throws Exception {

		/* 로그인 체크 */
		HttpSession session = request.getSession();
		session.setAttribute("userEmail", user.getUserEmail());

		return "/layout/header";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView mainPage(UserDto user, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("main");
		List<MusicDto> musicList = mcService.importMusicInfo();
		Stream<MusicDto> stream = musicList.stream();
		stream.forEach(s -> {
			int userPk = s.getUserPk();
			List<UserDto> users = new ArrayList<UserDto>();
			try {
				users =	memberService.userInfoByPk(userPk);	
				mv.addObject("users", users);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		});
		
		mv.addObject("music", musicList);
		
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchResultPage(@RequestParam("keyword") String keyword) throws Exception {
		ModelAndView mv = new ModelAndView("searchresult");
		List<MusicDto> musicList = mcService.searchMusic(keyword);
		mv.addObject("keyword", keyword);
		mv.addObject("search", musicList);
		
		Stream<MusicDto> musicListStream = musicList.stream();
		musicListStream.forEach(s -> {
			int userPk = s.getUserPk();
			List<UserDto> users = new ArrayList<UserDto>();
			try {
				users = memberService.userInfoByPk(userPk);
				mv.addObject("users",users);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchPage(UserDto user, @RequestParam("keyword") String keyword, Model model)
			throws Exception {
		List<MusicDto> musicList = mcService.searchMusic(keyword);

		model.addAttribute("musicList", musicList);
		return "searchresult";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detailPage() throws Exception {
		return "detail";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(UserDto user) throws Exception {

		return "/member/login";
	}

	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public @ResponseBody String emailCheck(UserDto user, HttpServletRequest request,
			@RequestParam("userEmail") String userEmail, @RequestParam("userPw") String userPw) throws Exception {
		int count = memberService.selectMemberInfoYn(userEmail, userPw);
		String str = "";

		if (count == 1) {
			str = "YES";
		} else {
			str = "NO";
		}
		return str;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object regiserCheck(UserDto memberDto) throws Exception {

		int result = memberService.emailCheck(memberDto.getUserEmail());

		HashMap<String, Integer> datas = new HashMap<>();
		datas.put("count", result);
		
		return datas;
	}
	
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(UserDto user, HttpServletRequest request) throws Exception {

		int count = memberService.selectMemberInfoYn(user.getUserEmail(), user.getUserPw());

		if (count == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("userEmail", user.getUserEmail());
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("userPk", user.getUserPk());
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

		return "/member/signin";
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String createUser(UserDto User) throws Exception {
		memberService.signinUser(User);

		return "redirect:/main";
	}
	
	
	
	/* ----- 유저 프로필 페이지(류주형) ----- */
	
	@RequestMapping("/profilelist")
	public ModelAndView addrList() throws Exception {
		ModelAndView mv = new ModelAndView("/member/userList");

		List<UserDto> userList = mcService.selectUserProfile();
		mv.addObject("userList", userList);

		return mv;
	}

	@RequestMapping("/profile")
	public ModelAndView userProfile(@RequestParam("userPk") int userPk) throws Exception {

		ModelAndView mv = new ModelAndView("/member/userprofile");

		UserDto sound = mcService.userProfile(userPk);
		List<MusicDto> upload = mcService.selectUserUpload(userPk);
		List<UserPlaylistDto> playList = mcService.selectUserPlaylist(userPk);
		mv.addObject("sound", sound);
		mv.addObject("upload", upload);
		mv.addObject("playList", playList);

		return mv;
	}
	
	
	/* ----- 로그인(배진국) ----- */
	

	// 회원 가입 Process
	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public String registerProcess(UserDto memberDto) throws Exception {
		
		memberService.register(memberDto);
		return "redirect:/";
	}
	
	// 이메일 중복 확인



	// 회원 정보 페이지
	@RequestMapping(value = "/memberInfo", method = RequestMethod.GET)
	public ModelAndView memberInfo(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
			
		int userPk =(int) session.getAttribute("userPk");
		
		UserDto data = memberService.selectMemberInfo(userPk);
	
		ModelAndView mv = new ModelAndView("/member/memberInfo");
		mv.addObject("data", data);
		
		return mv;
	}
	
	// 회원 정보 수정 Process
	@RequestMapping(value = "/memberInfo", method = RequestMethod.PUT)
	public String update(UserDto memberDto) throws Exception { 

		memberService.update(memberDto);
		 
		return "/index";
	}
		
	// 회원 탈퇴 Process
	@RequestMapping(value = "/memberInfo/{userPk}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("userPk") int userPk) throws Exception { 
		memberService.delete(userPk);
		
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

		return memberService.findpwProcess(hashMap);
	}
	
	// 마이 페이지
	@RequestMapping("/myPage")
	public Object myPage(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		int userPk = (int) session.getAttribute("userPk");
		
		ModelAndView mv = new ModelAndView("/myPage");
		
		List<MusicReplyDto> replyData = mcService.reply(userPk); 
		return mv;
	}
	
	

	
	/* ----- 업로드(최정환) ----- */

	@RequestMapping(value="/upload")
	public ModelAndView openIndex() throws Exception{
		ModelAndView mv = new ModelAndView("/file_IO/upload_music");
		
		return mv;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public Map<String, Object> insertProductRegistration(MultipartHttpServletRequest multiFiles) throws Exception {		
		
		ioService.insertAudio(multiFiles);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("SUCCESS", true);
		
		return result;
	}
	
	
	
	@RequestMapping(value="/audio")
	public ModelAndView openMusicDetail() throws Exception{
		ModelAndView mv = new ModelAndView("/detail");
		return mv;
	}
	
	@RequestMapping(value="/audio/1")
	public String musicSrc() throws Exception{
		
		return "C:/Users/CJH/git/Final_Project_1Team/music_cloud/audio/20220317/438724586989200.flac";
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
