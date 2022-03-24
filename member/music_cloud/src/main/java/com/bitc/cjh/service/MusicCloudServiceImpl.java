package com.bitc.cjh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.dto.MusicReplyDto;
import com.bitc.cjh.dto.ReplyDto;
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
		Stream<MusicDto> searchListStream = musicList.stream();
		Stream<MusicDto> musicListStream = musicList.stream();
		musicListStream.forEach(s -> {
			int userPk = s.getUserPk();
			List<UserDto> users = new ArrayList<UserDto>();
			try {
				users = mcMapper.userInfoByPk(userPk);
				s.setUserList(users);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		});
		
		searchListStream.forEach(m -> {
			int musicPk = m.getMusicPk();
			List<FileDto> files = new ArrayList<FileDto>();
			try {
				files = mcMapper.findFilePath(musicPk);
				m.setFileList(files);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		return musicList;
	}

	@Override
	public List<MusicDto> importMusicInfo() throws Exception {
		List<MusicDto> musicList = mcMapper.importMusicInfo();
		Stream<MusicDto> listStream = musicList.stream();
		listStream.forEach(s -> {
			int userPk = s.getUserPk();
			List<UserDto> users = new ArrayList<UserDto>();
			try {
				users = mcMapper.userInfoByPk(userPk);
				s.setUserList(users);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		
		return musicList;
	}

	@Override
	public List<MusicDto> musicInfoByCategory() throws Exception {
		List<MusicDto> maxLikeList = mcMapper.musicInfoByCategory();
		Stream<MusicDto> listStream = maxLikeList.stream();
		listStream.forEach(s -> {
			int userPk = s.getUserPk();
			List<UserDto> users = new ArrayList<UserDto>();
			try {
				users = mcMapper.userInfoByPk(userPk);
				s.setUserList(users);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		
		return maxLikeList;
	}
	
	@Override
	public MusicDto viewDetailPage(int musicPk) throws Exception {
		MusicDto MusicDetail = mcMapper.viewDetailPage(musicPk);
		List<FileDto> files = mcMapper.findFilePath(musicPk);
		int userPk = MusicDetail.getUserPk();
		List<UserDto> users = mcMapper.userInfoByPk(userPk);
		MusicDetail.setFileList(files);
		MusicDetail.setUserList(users);
		return MusicDetail;
	}
	
	
	@Override
	public List<MusicReplyDto> reply(int userPk) throws Exception {
		return mcMapper.reply(userPk);
	}

	
	@Override
	public ArrayList<MusicDto> music(int userPk) throws Exception {
		return mcMapper.music(userPk);
	}
	
	@Override
	public ArrayList<FileDto> file(String userEmail) throws Exception {
		return (ArrayList<FileDto>) mcMapper.file(userEmail);
	}
	
	@Override
	public void fileDelete(String fileName) throws Exception {
		mcMapper.fileDelete(fileName);
	}
	/* ----- 회원 프로필 ----- */

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
	
	@Override
	public List<MusicDto> checkMusicInfo(int musicPk) throws Exception {
		List<MusicDto> musicInfo = mcMapper.checkMusicInfo(musicPk);
		return musicInfo;
	}

	@Override
	public List<FileDto> checkFileInfo(int musicPk) throws Exception {
		List<FileDto> fileInfo = mcMapper.checkFileInfo(musicPk);
		return fileInfo;
	}

	@Override
	public List<ReplyDto> selectMusicReplyList(int pk) throws Exception {
		return mcMapper.selectMusicReplyList(pk);
	}

	@Override
	public void insertMusicReply(ReplyDto reply) throws Exception {
		mcMapper.insertMusicReply(reply);
		
	}







	



}
