package com.bitc.cjh.dto;

import java.util.List;

import lombok.Data;

@Data
public class MusicDto {
	
	private int musicPk;
	private int musicCategoryPk;
	private int userPk;
	
	private String musicTitle;
	private String musicArtist;
	private int genre;
	private String musicAlbum;
	private String musicDesc;
	
	private int likeCount;
	private char privacy;
	
	private String createdDt;
	private List<FileDto> fileList;
	private List<UserDto> userList;
	
}
