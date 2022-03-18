package com.bitc.cjh.service;

import java.util.List;

import com.bitc.cjh.dto.MusicCloudMusicDto;
import com.bitc.cjh.dto.MusicCloudUserDto;

public interface MusicCloudService {

	void signinUser(MusicCloudUserDto user) throws Exception;

	int selectMemberInfoYn(String userEmail, String userPw) throws Exception;

	List<MusicCloudMusicDto> searchMusic(String keyword) throws Exception;

	List<MusicCloudUserDto> userInfoByPk(int userPk) throws Exception;

}
