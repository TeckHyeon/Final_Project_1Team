package com.bitc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bitc.dto.SoundDto;

@Mapper
public interface SoundMapper {

	public List<SoundDto> selectUserProfile() throws Exception;

	public SoundDto userProfile(int userPk) throws Exception;
	
	public List<SoundDto> selectUserPlaylist(int userPk) throws Exception;
	
	public List<SoundDto> selectUserUpload(int userPk) throws Exception;
	
	public List<SoundDto> selectUserPlaylistDetail(int userPlaylistPk) throws Exception;
	
	public SoundDto selectUserPlaylistTitle(int userPlaylistPk) throws Exception;

}
