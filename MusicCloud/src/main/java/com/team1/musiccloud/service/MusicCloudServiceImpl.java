package com.team1.musiccloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team1.musiccloud.dto.MusicCloudUserDto;
import com.team1.musiccloud.mapper.MusicCloudMapper;

@Service
public class MusicCloudServiceImpl implements MusicCloudService {

	@Autowired
	private MusicCloudMapper musicMapper;
	
	@Override
	public void signinUser(MusicCloudUserDto user) throws Exception {
		musicMapper.signinUser(user);
		
	}

	@Override
	public int selectMemberInfoYn(String userEmail, String userPw) throws Exception {
		// TODO Auto-generated method stub
		return musicMapper.selectMemberInfoYn(userEmail, userPw);
	}

}
