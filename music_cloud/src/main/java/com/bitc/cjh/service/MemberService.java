package com.bitc.cjh.service;

import java.util.HashMap;
import java.util.List;

import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.UserDto;

public interface MemberService {

	/*회원 로그인 및 가입 관련 기능*/
	
	/* ----- 강도현 ----- */
	void signinUser(UserDto user) throws Exception;
	
	int selectMemberInfoYn(String userEmail, String userPw) throws Exception;
	
	List<UserDto> userInfoByPk(int userPk) throws Exception;
	
	
	/* ----- 배진국 ----- */
	void register(UserDto memberDto) throws Exception;

	void update(UserDto memberDto) throws Exception;

	void delete(int userPk) throws Exception;

	//이메일 중복 체크
	int emailCheck(String userEmail) throws Exception;

	UserDto selectMemberInfo(int userPk) throws Exception;

	int findId(UserDto memberDto) throws Exception;

	String findGetId(UserDto memberDto) throws Exception;

	int findPw(UserDto memberDto) throws Exception;

	void updateTempPw(UserDto memberDto) throws Exception;

	Object findpwProcess(HashMap<String, Object> hashMap) throws Exception;
	
}
