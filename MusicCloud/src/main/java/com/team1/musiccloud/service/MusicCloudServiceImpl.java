package com.team1.musiccloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team1.musiccloud.dto.MusicCloudMusicDto;
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

	@Override
	public List<MusicCloudMusicDto> searchMusic(String keyword) throws Exception {
		List<MusicCloudMusicDto> musicList = musicMapper.searchMusic(keyword);
		return musicList;
	}

	@Override
	public List<MusicCloudUserDto> userInfoByPk(int userPk) throws Exception {
		List<MusicCloudUserDto> users = musicMapper.userInfoByPk(userPk);
		return users;
	}

}