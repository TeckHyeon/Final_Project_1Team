package com.bitc.cjh.service;

import java.util.List;

import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.UserDto;
import com.bitc.cjh.dto.UserPlaylistDto;

public interface MusicCloudService {

	/* 음악 검색(강도현) */
	List<MusicDto> searchMusic(String keyword) throws Exception;

	List<MusicDto> importMusicInfo() throws Exception;

	/* 회원 댓글(배진국) */
	List<MusicReplyDto> reply(int userPk) throws Exception;

	/* 회원 프로필 조회(류주형) */
	List<UserDto> selectUserProfile() throws Exception;

	UserDto userProfile(int userPk) throws Exception;

	List<UserPlaylistDto> selectUserPlaylist(int userPk) throws Exception;

	List<MusicDto> selectUserUpload(int userPk) throws Exception;

	List<MusicDto> musicInfoByCategory() throws Exception;

	MusicDto viewDetailPage(int musicPk) throws Exception;

	List<MusicDto> checkMusicInfo(int musicPk) throws Exception;

	List<FileDto> checkFileInfo(int musicPk) throws Exception;

}
