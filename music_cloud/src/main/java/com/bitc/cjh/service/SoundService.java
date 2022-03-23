package com.bitc.cjh.service;

import java.util.List;

import com.bitc.cjh.dto.SoundDto;

public interface SoundService {
	
	List<SoundDto> selectUserProfile() throws Exception;
	
	SoundDto userProfile(int userPk) throws Exception;
	
	List<SoundDto> selectUserPlaylist(int userPk) throws Exception;
	
}
