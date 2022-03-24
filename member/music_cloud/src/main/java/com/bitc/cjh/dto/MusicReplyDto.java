package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class MusicReplyDto {

	int musicReplyPk; 
	int musicPk;
	int userPk;
	String reply; 
	String deletedYn;
}
