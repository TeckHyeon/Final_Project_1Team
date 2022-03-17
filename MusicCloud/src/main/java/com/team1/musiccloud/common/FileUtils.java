package com.team1.musiccloud.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.team1.musiccloud.dto.FileDto;



//@Component : 스프링프레임워크가 자동으로 등록하여 객체를 실행할 자바 클래스(임의 작성), @Bean과 동일한 기능 but 사용자가 필요에 따라서 작성해 추가하는 라이브러리
//@Bean : 스프링프레임워크에에 사전 등록되어 있는 라이브러리. 
@Component 
public class FileUtils {
	
	public List<FileDto> parseFileInfo(MultipartHttpServletRequest multiFiles) throws Exception {
		
		//매개변수로 받은 파일정보가 없을 경우 null을 리턴 후 메서드 종료
		if(ObjectUtils.isEmpty(multiFiles)) {
			return null;
		}
	
		//BoardFileDto 클래스에서 사용하는 정보만 추려내서 리스트로 생성
		List<FileDto> fileList = new ArrayList<>();
		//날짜 사용 형식을 지정
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		//시간 경계선, 현재 지역의 시간대를 기준으로 함
		ZonedDateTime current = ZonedDateTime.now();
		//현재 시간을 정의한 format으로 출력하겠다는 명령
		//이미지 파일 저장경로 설정
		String path = "audio/" + current.format(format);
		
		//서버로 os로 주로 사용되는 Linux나 unix는 폴더 및 파일, 각종 드라이버를 파일로 인식함
		File file = new File(path);
		
		if(file.exists() == false) {
			file.mkdirs();//폴더 생성
		}
		
		//매개변수로 받아온 파일 정보에서 모든 파일 이름을 가져옴
		Iterator<String> iterator = multiFiles.getFileNames();
		String newFileName;
		String originalFileExtension;
		String contentType;
		
		while(iterator.hasNext()) {
			
			String name = iterator.next();
			List<MultipartFile> list = multiFiles.getFiles(name);
			
			for(MultipartFile mFile : list) {
				if(mFile.isEmpty() == false) {
					contentType = mFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("audio/opus")) {
							originalFileExtension = ".opus";
						}
						else if (contentType.contains("audio/flac")) {
							originalFileExtension = ".flac";
						}
						else if (contentType.contains("audio/webm")) {
							originalFileExtension = ".webm";
						}
						else if (contentType.contains("audio/weba")) {
							originalFileExtension = ".weba";
						}
						else if (contentType.contains("audio/wav")) {
							originalFileExtension = ".wav";
						}
						else if (contentType.contains("audio/ogg")) {
							originalFileExtension = ".ogg";
						}
						else if (contentType.contains("audio/m4a")) {
							originalFileExtension = ".m4a";
						}
						else if (contentType.contains("audio/mpeg")) {
							originalFileExtension = ".mp3";
						}
						else if (contentType.contains("audio/oga")) {
							originalFileExtension = ".oga";
						}
						else if (contentType.contains("audio/mid")) {
							originalFileExtension = ".mid";
						}
						else if (contentType.contains("audio/amr")) {
							originalFileExtension = ".amr";
						}
						else if (contentType.contains("audio/aiff")) {
							originalFileExtension = ".aiff";
						}
						else if (contentType.contains("audio/wma")) {
							originalFileExtension = ".wma";
						}
						else if (contentType.contains("audio/au")) {
							originalFileExtension = ".au";
						}
						else if (contentType.contains("audio/aac")) {
							originalFileExtension = ".aac";
						}
						else {
							break;
						}
					}
					
					//현재시간을 기준으로 이름을 설정함
					//1970년 1월 1일 00시 00분 00초 기준으로 현재시간까지 진행된 시간을 1000분의 1로 표현
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					FileDto audioFile = new FileDto();
					//boardFile.setIdx(boardIdx);
					//현재파일의 크기
					//MultipartFile 클래스의 getSize() 메서드의 반환타입이 long이기 때문에 sql 문에서 1메가가 넘어가는 경우
					//1,xxx로 표시되어 문자열로 인식 되기 때문에 그대로 사용하지 못하고 문자열로 변환하여 사용
					audioFile.setFileSize(Long.toString(mFile.getSize()));
					//현재 파일의 실제 파일명
					audioFile.setOriginalFileName(mFile.getOriginalFilename());
					//새로 생성된 파일명과 실제 저장할 경로를 합하여 디스크에 저장할 경로 및 파일명 설정
					audioFile.setStoredFilePath(path + "/" + newFileName);
					
					//목록에 저장
					fileList.add(audioFile);
					
					file = new File(path+"/"+newFileName);
					//현재 파일(메모리에만 존재된 상태)을 지정한 위치로 이동(실제 디스크에 저장)
					mFile.transferTo(file);
				}
			}
		}		
		
		//모든 파일 정보를 가지고 있는 리스트를 반환
		return fileList;
	}

}
