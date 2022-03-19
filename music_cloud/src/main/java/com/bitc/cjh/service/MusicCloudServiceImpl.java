package com.bitc.cjh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.UserDto;
import com.bitc.cjh.dto.UserPlaylistDto;
import com.bitc.cjh.mapper.MusicCloudMapper;


@Service
public class MusicCloudServiceImpl implements MusicCloudService {

	@Autowired
	private MusicCloudMapper mcMapper;

	@Override
	public List<MusicDto> searchMusic(String keyword) throws Exception {
		List<MusicDto> musicList = mcMapper.searchMusic(keyword);
		return musicList;
	}
	@Override
	public List<MusicDto> importMusicInfo() throws Exception {
		
		return mcMapper.importMusicInfo();
	}
	
	@Override
	public List<MusicReplyDto> reply(int userPk) throws Exception {
		return mcMapper.reply(userPk);
	}


	/* ----- 회원 프로필 -----*/

	@Override
	public List<UserDto> selectUserProfile() throws Exception {
		return mcMapper.selectUserProfile();
	}

	@Override
	public UserDto userProfile(int userPk) throws Exception {
		return mcMapper.userProfile(userPk);
	}

	@Override
	public List<UserPlaylistDto> selectUserPlaylist(int userPk) throws Exception {
		return mcMapper.selectUserPlaylist(userPk);
	}

	@Override
	public List<MusicDto> selectUserUpload(int userPk) throws Exception {
		return mcMapper.selectUserUpload(userPk);
	}



}
