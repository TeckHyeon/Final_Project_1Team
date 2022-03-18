package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class MusicCloudMusicDto {
	private int musicPk;
	private int musicCategoryPk;
	private int userPk;
	private String musicTitle;
	private String musicDesc;
	private String likeCount;
	private String privacy;
}
