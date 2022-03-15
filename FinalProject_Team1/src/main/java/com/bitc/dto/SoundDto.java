package com.bitc.dto;

import lombok.Data;

@Data
public class SoundDto {

	private int userPk;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userPhone;
	private String likeList;
	private char adminYn;
	private int userPlaylistPk;
	private String userPlaylistName;
	private String playlist;
	private int musicPk;
	private int musicCategoryPk;
	private String musicTitle;
	private String likeCount;
	private char privacy;

}
