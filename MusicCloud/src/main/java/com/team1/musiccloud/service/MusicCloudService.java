package com.team1.musiccloud.service;

import java.util.List;

import com.team1.musiccloud.dto.MusicCloudMusicDto;
import com.team1.musiccloud.dto.MusicCloudUserDto;

public interface MusicCloudService {

	void signinUser(MusicCloudUserDto user) throws Exception;

	int selectMemberInfoYn(String userEmail, String userPw) throws Exception;

	List<MusicCloudMusicDto> searchMusic(String keyword) throws Exception;

	List<MusicCloudUserDto> userInfoByPk(int userPk) throws Exception;

}
