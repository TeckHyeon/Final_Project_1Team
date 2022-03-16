package com.team1.musiccloud.service;

import com.team1.musiccloud.dto.MusicCloudUserDto;

public interface MusicCloudService {

	void signinUser(MusicCloudUserDto user) throws Exception;

	int selectMemberInfoYn(String userEmail, String userPw) throws Exception;

}
