package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class ReplyDto {
	
	int musicReplyPk;
	int musicPk;
	int userPk;
	String userName;
	String reply;

}
