package com.bitc.dto;

import lombok.Data;

@Data
public class MusicReplyDto {

	private int musicReplyPk; 
	private int musicPk;
	private int userPk;
	private String reply; 
	private String deletedYn;
}
