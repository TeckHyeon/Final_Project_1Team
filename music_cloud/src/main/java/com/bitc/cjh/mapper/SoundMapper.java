package com.bitc.cjh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bitc.cjh.dto.SoundDto;

@Mapper
public interface SoundMapper {

	public List<SoundDto> selectUserProfile() throws Exception;

	public SoundDto userProfile(int userPk) throws Exception;
	
	public List<SoundDto> selectUserPlaylist(int userPk) throws Exception;

}
