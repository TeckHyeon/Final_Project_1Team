package com.bitc.service;

import java.util.HashMap;
import java.util.List;

import com.bitc.dto.MusicReplyDto;
import com.bitc.dto.UsersDto;

public interface MusicService {

	void register(UsersDto memberDto) throws Exception;

	void update(UsersDto memberDto) throws Exception;

	void delete(int userPk) throws Exception;

	int emailCheck(String userEmail) throws Exception;

	UsersDto selectMemberInfo(int userPk) throws Exception;

	int findId(UsersDto memberDto) throws Exception;

	String findGetId(UsersDto memberDto) throws Exception;

	int findPw(UsersDto memberDto) throws Exception;

	void updateTempPw(UsersDto memberDto) throws Exception;

	Object findpwProcess(HashMap<String, Object> hashMap) throws Exception;

	List<MusicReplyDto> reply(int userPk) throws Exception;

}
