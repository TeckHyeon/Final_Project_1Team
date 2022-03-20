package com.bitc.cjh.dto;

import lombok.Data;

@Data
public class MusicCategoryDto {
	
	private int musicCategoryPk;
	private String musicCategoryName;
	private int musicCategoryPkParents;
	
}
