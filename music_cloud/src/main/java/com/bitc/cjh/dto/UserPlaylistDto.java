package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class UserPlaylistDto {


	private int userPlaylistPk;
	private int userPk;
	private String userPlaylistName;
	private String playlist;
	
}
