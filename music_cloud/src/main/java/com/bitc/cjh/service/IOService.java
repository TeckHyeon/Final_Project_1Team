package com.bitc.cjh.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;

//Controller에서 사용할 비즈니스 로직의 사용방법을 제공
public interface IOService {
	
	//DB 관련 추상 메서드(abstract 생략 가능)
	FileDto insertAudio(MultipartHttpServletRequest multiFiles) throws Exception;
	
	void insertMusicDesc(MusicDto music) throws Exception;

	//FileDto selectBoardFileInfo(@Param("fileIdx") int fileIdx, @Param("idx") int idx) throws Exception;
}
