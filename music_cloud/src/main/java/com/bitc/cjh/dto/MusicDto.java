package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class MusicDto {
	
	private int musicPk;
	private int musicCategoryPk;
	private int userPk;
	private String musicTitle;
	private String musicDesc;
	private int likeCount;
	private char privacy;
	private String createdDt;
	
}
