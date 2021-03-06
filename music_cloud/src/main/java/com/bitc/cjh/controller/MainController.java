package com.bitc.cjh.controller;

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

import com.bitc.cjh.common.AudioTagger;
import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.ReplyDto;
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
	
	public static final String AUDIO_PATH = "C:\\Users\\CJH\\git\\Final_Project_1Team\\music_cloud\\audio";
	public static final int BYTE_RANGE = 128;
		
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
	
	@RequestMapping(value = "/footer", method = RequestMethod.GET)
	@ResponseBody
	public Object currentPlayLits(@RequestParam("musicPk") int musicPk) throws Exception {
		List<MusicDto> musicInfo = mcService.checkMusicInfo(musicPk);
		List<FileDto> fileInfo = mcService.checkFileInfo(musicPk);
		Stream<MusicDto> musicInfoStream = musicInfo.stream();
		Map<String, Object> retVal = new HashMap<String, Object>();
		musicInfoStream.forEach(s -> {
			try {
				String title = s.getMusicTitle();
				String artist = s.getMusicArtist();

				retVal.put("title", title);
				retVal.put("artist", artist);

			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		Stream<FileDto> fileInfoStream = fileInfo.stream();
		fileInfoStream.forEach(f -> {
			try {
				String src = f.getStoredFilePath();
				retVal.put("src", src);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		return retVal;
	}
	
	@RequestMapping(value = "/footer", method = RequestMethod.POST)
	@ResponseBody
	public Object currentPlayList(@RequestParam("musicPk") int musicPk, HttpServletRequest request,
			@RequestParam("title") String title, @RequestParam("artist") String artist, @RequestParam("src") String src)
			throws Exception {

		Map<String, Object> retVal = new HashMap<String, Object>();
		retVal.put("title", title);
		retVal.put("artist", artist);
		retVal.put("src", src);


		return retVal;
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView mainPage(UserDto user, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("main");
		List<MusicDto> musicList = mcService.importMusicInfo();

		List<MusicDto> maxLikeList = mcService.musicInfoByCategory();
		mv.addObject("music", musicList);
		mv.addObject("like", maxLikeList);
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchResultPage(@RequestParam("keyword") String keyword) throws Exception {
		ModelAndView mv = new ModelAndView("searchresult");
		List<MusicDto> musicList = mcService.searchMusic(keyword);
		mv.addObject("keyword", keyword);
		mv.addObject("search", musicList);
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchPage(UserDto user, @RequestParam("keyword") String keyword, Model model) throws Exception {
		List<MusicDto> musicList = mcService.searchMusic(keyword);

		model.addAttribute("musicList", musicList);
		return "searchresult";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detailPage(@RequestParam("musicPk") int musicPk) throws Exception {
		ModelAndView mv = new ModelAndView("/detail");
		MusicDto MusicDetail = mcService.viewDetailPage(musicPk);
		mv.addObject("MusicDetail", MusicDetail);
		return mv;
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
			
			UserDto userInfo = memberService.selectMember(user.getUserEmail());
			
			HttpSession session = request.getSession();
			
			session.setAttribute("userEmail", userInfo.getUserEmail());
			session.setAttribute("userName", userInfo.getUserName());
			session.setAttribute("userPk", userInfo.getUserPk());
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

	@RequestMapping("/profile{userPk}")
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


	// 회원 정보 페이지
	@RequestMapping(value = "/memberInfo", method = RequestMethod.GET)
	public ModelAndView memberInfo(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
			
		int userPk =(int) session.getAttribute("userPk");
		
		UserDto data = memberService.selectMemberInfo(userPk);
	
		ModelAndView mv = new ModelAndView("/login/memberInfo");
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
		
		ModelAndView mv = new ModelAndView("/login/myPage");
		
		List<MusicReplyDto> replyData = mcService.reply(userPk); 
		mv.addObject("replyData", replyData);
		return mv;
	}
	
	

	
	/* ----- 업로드(최정환) ----- */

	@RequestMapping(value="/upload")
	public ModelAndView openUpload() throws Exception{
		ModelAndView mv = new ModelAndView("/file/upload_music");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public Map<String, Object> insertAudio(MultipartHttpServletRequest multiFiles, HttpServletRequest request) throws Exception {		
		
		HttpSession session = request.getSession();
		
		int userPk = (int)session.getAttribute("userPk");
		
		FileDto music = ioService.insertAudio(multiFiles, userPk);
		
		session.setAttribute("tag", music.getTag());
		session.setAttribute("path", music.getStoredThumbPath());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("SUCCESS", true);
		
		return result;
	}
	
	@RequestMapping(value="/upload/desc", method=RequestMethod.GET)
	public ModelAndView openUploadDesc(HttpServletRequest request) throws Exception {		
		
		HttpSession session = request.getSession();
		
		AudioTagger tag = (AudioTagger)session.getAttribute("tag");
		String thumb_path = (String)session.getAttribute("path");
		
		session.removeAttribute("tag");
		session.removeAttribute("path");
		
		MusicDto music = new MusicDto();
		
		music.setMusicTitle(tag.getTitle());
		music.setMusicArtist(tag.getArtist());
		music.setMusicAlbum(tag.getAlbum());
		music.setGenre(tag.getGenre());
		
		ModelAndView mv = new ModelAndView("file/music_desc");
		
		mv.addObject("tag", music);
		mv.addObject("thumb_path", thumb_path);
		
		
		return mv;
	}
	
	@RequestMapping(value="/upload/desc", method=RequestMethod.POST)
	public String insertMusicDesc(MusicDto music, HttpServletRequest request) throws Exception {
		
		System.out.println(music.toString());
		
		HttpSession session = request.getSession();
	
		int userPk = (Integer)session.getAttribute("userPk");
		
		music.setUserPk(userPk);
		
		ioService.insertMusicDesc(music);
		
	
		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public Map<String, Object> addReply(ReplyDto reply, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
	
		int userPk = (Integer)session.getAttribute("userPk");
		String userName = (String)session.getAttribute("userName");
		
		reply.setUserPk(userPk);
		reply.setUserName(userName);
		
		mcService.insertMusicReply(reply);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "success");
		result.put("userName", userName);
		result.put("reply", reply.getReply());
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/reply/{musicPk}", method=RequestMethod.POST)
	public List<ReplyDto> selectMusicReplyList(@PathVariable("musicPk") String musicPk) throws Exception {
		
		int pk = Integer.parseInt(musicPk);
		
		List<ReplyDto> list = mcService.selectMusicReplyList(pk);
		
		
		return list;
	}
	
	
	
	
	/*
	@RequestMapping(value="/audio")
	public ModelAndView openMusicDetail() throws Exception{
		ModelAndView mv = new ModelAndView("/detail");
		return mv;
	}
	
	@RequestMapping(value="/audio/{fileName}", method=RequestMethod.GET)
	public Mono<ResponseEntity<byte[]>> streamAudio(
			@RequestHeader(value = "Range", required = false) String httpRangeList,
            @PathVariable("fileName") String fileName) {
		
		return Mono.just(getContent(AUDIO_PATH, fileName, httpRangeList, "audio"));
	}
	
	
	private ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix) {
		
		long rangeStart = 0;
		long rangeEnd;
		byte[] data;
		Long fileSize;
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		System.out.println(System.getProperty("user.dir"));
		
		try {
			fileSize = Optional.ofNullable(fileName)
					  .map(file -> Paths.get(location, file))
					  .map(this :: sizeFromFile)
					  .orElse(0L);
			if (range == null) {
				return ResponseEntity.status(HttpStatus.OK)
					  .header("Content-Type", contentTypePrefix+"/"+fileType)
					  .header("Content-Length", String.valueOf(fileSize))
					  .body(readByteRange(location, fileName, rangeStart, fileSize - 1));
			}
			
			String[] ranges = range.split("-");
			rangeStart = Long.parseLong(ranges[0].substring(6));
			
			if (ranges.length > 1) {
				rangeEnd = Long.parseLong(ranges[1]);
			} else {
				rangeEnd = fileSize - 1;
			}
			
			if (fileSize < rangeEnd) {
				rangeEnd = fileSize - 1;
			}
			
			data = readByteRange(location, fileName, rangeStart, rangeEnd);
			
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
		
		return ResponseEntity.status(HttpStatus.OK)
			  .header("Content-Type", contentTypePrefix+"/"+fileType)
			  .header("Content-Length", contentLength)
			  .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
			  .body(data);
		
	}
	
	public byte[] readByteRange(String location, String fileName, long start, long end) throws IOException {
		Path path = Paths.get(location, fileName);
		
		try (InputStream inputStream = (Files.newInputStream(path));
			ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
			
				byte[] data = new byte[BYTE_RANGE];
			    int nRead;
			    
			    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			       bufferedOutputStream.write(data, 0, nRead);
			    }
			    
			    bufferedOutputStream.flush();
			    
			    byte[] result = new byte[(int) (end - start) + 1];
			    
			    System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
			    
			    return result;
			}
				
	}
	
	
	private String getFilePath(String location) {
	    URL url = this.getClass().getResource(location);
	    return new File(url.getFile()).getAbsolutePath();
	}
	
	
	
	private Long sizeFromFile(Path path) {
		try {
		   return Files.size(path);
		} catch (IOException ex) {
		   ex.printStackTrace();
		}
		return 0L;
	}
	*/
}
