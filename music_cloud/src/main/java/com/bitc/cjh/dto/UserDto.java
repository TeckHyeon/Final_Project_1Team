package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private int userPk;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userPhone;
	private String likeList;
	private String adminYn;

}
