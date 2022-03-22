package com.bitc.cjh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.cjh.common.FileUtils;
import com.bitc.cjh.dto.FileDto;
import com.bitc.cjh.dto.MusicDto;
import com.bitc.cjh.mapper.IOMapper;

//내부에서 자바 로직을 처리하는 어노테이션
//지정한 interface 대신 실행하는 의미
@Service
public class IOServiceImpl implements IOService {
	
	@Autowired
	private IOMapper ioMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public FileDto insertAudio(MultipartHttpServletRequest multiFiles) throws Exception {
		
		//매개변수로 파일 정보를 분석하여 BoardFileDto클래스의 List로 생성
		List<FileDto> list = fileUtils.parseFileInfo(multiFiles);
		
		if(CollectionUtils.isEmpty(list) == false) {
			ioMapper.insertAudio(list);
		}
		
		return list.get(0);
	}

	@Override
	public void insertMusicDesc(MusicDto music) throws Exception {
		
		ioMapper.insertMusicDesc(music);
	}
}
