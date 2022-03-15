package com.bitc.service;

import java.util.List;

import com.bitc.dto.SoundDto;

public interface SoundService {
	
	List<SoundDto> selectUserProfile() throws Exception;
	
	SoundDto userProfile(int userPk) throws Exception;
	
	List<SoundDto> selectUserPlaylist(int userPk) throws Exception;
	
}
