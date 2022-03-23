package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class UserCurrentPlaylistDto {


	private int idx;
	private int musicPk;
	private String musicTitle;
	private String musicArtist;
	private String storedFilePath;
	private int userPk;
}
