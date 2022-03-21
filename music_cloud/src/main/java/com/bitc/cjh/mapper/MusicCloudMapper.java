package com.bitc.cjh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.UserDto;
import com.bitc.cjh.dto.UserPlaylistDto;


@Mapper
public interface MusicCloudMapper {


	List<MusicDto> searchMusic(@Param("keyword")String keyword) throws Exception;

	List<MusicReplyDto> reply(@Param("userPk") int userPk) throws Exception;
	
	public List<UserDto> selectUserProfile() throws Exception;

	public UserDto userProfile(int userPk) throws Exception;
	
	public List<UserPlaylistDto> selectUserPlaylist(int userPk) throws Exception;
	
	public List<MusicDto> selectUserUpload(int userPk) throws Exception;

	List<MusicDto> importMusicInfo() throws Exception;

	List<MusicDto> musicInfoByCategory() throws Exception;

	List<FileDto> findFilePath(int musicPk) throws Exception;

	List<UserDto> userInfoByPk(int userPk) throws Exception;

	MusicDto viewDetailPage(int musicPk) throws Exception;

}
