package com.bitc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.dto.SoundDto;
import com.bitc.mapper.SoundMapper;

@Service
public class SoundServiceImpl implements SoundService {

	@Autowired
	private SoundMapper soundMapper;

	@Override
	public List<SoundDto> selectUserProfile() throws Exception {
		return soundMapper.selectUserProfile();
	}

	@Override
	public SoundDto userProfile(int userPk) throws Exception {
		return soundMapper.userProfile(userPk);
	}

	@Override
	public List<SoundDto> selectUserPlaylist(int userPk) throws Exception {
		return soundMapper.selectUserPlaylist(userPk);
	}

}
