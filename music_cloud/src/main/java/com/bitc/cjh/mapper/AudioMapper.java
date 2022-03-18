package com.bitc.cjh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bitc.cjh.dto.FileDto;


//mybatis와 연결되어 있는 interface 라는 것을 의미하는 어노테이션
@Mapper
public interface AudioMapper {
	
	void insertAudio(List<FileDto> list) throws Exception;

	//DB에 연결하여 게시글의 첨부파일 정보를 가져오는 메서드
	//@Param : 매개변수로 받아온 파일에 대한 설명을 지정하는 어노테이션
	//mapper에서 @Param으로 지정한 이름을 xml 파일에서 사용할 수 있음
	//FileDto selectBoardFileInfo(@Param("fileIdx") int fileIdx, @Param("idx") int idx) throws Exception;

}
