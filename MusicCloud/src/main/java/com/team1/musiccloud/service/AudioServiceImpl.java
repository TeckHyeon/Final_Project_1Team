package com.team1.musiccloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.team1.musiccloud.common.FileUtils;
import com.team1.musiccloud.dto.FileDto;
import com.team1.musiccloud.mapper.AudioMapper;


//내부에서 자바 로직을 처리하는 어노테이션
//지정한 interface 대신 실행하는 의미
@Service
public class AudioServiceImpl implements AudioService {
	
	@Autowired
	private AudioMapper audioMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public void insertAudio(MultipartHttpServletRequest multiFiles) throws Exception {
		
		//매개변수로 파일 정보를 분석하여 BoardFileDto클래스의 List로 생성
		List<FileDto> list = fileUtils.parseFileInfo(multiFiles);
		
		if(CollectionUtils.isEmpty(list) == false) {
			audioMapper.insertAudio(list);
		}
		
		/*
		//ObjectUtils : 스프링 프레임워크에서 지원하는 유틸 클래스, 지정한 객체가 비었는지 아닌지를 판단함
		if(ObjectUtils.isEmpty(multiFiles) == false) {
			Iterator<String> iterator = multiFiles.getFileNames();
			String name;
			
			while(iterator.hasNext()) {
				name = iterator.next();
				System.out.println("file tag name : " + name);
				
				//파일에 대한 정보를 MultiparFile 클래스로 생성한 list 타입으로 반환
				List<MultipartFile> fileist = multiFiles.getFiles(name);
				
				for(MultipartFile mFile : list) {
					System.out.println("\n\n----- Start file info -----\n");
					System.out.println("file name : " + mFile.getOriginalFilename());
					System.out.println("file size : " + mFile.getSize());
					System.out.println("file content type : " + mFile.getContentType());
					System.out.println("\n----- End file info ------\n");
				}
			}
		}*/
	}
}
