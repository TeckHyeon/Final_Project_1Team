package com.team1.musiccloud.dto;

import lombok.Data;

@Data
public class MusicCloudUserDto {
	private int userPk;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userPhone;
	private String adminYn;
}
