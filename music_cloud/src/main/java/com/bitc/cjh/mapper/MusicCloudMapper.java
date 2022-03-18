package com.bitc.cjh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.cjh.dto.MusicCloudMusicDto;
import com.bitc.cjh.dto.MusicCloudUserDto;

@Mapper
public interface MusicCloudMapper {

	void signinUser(MusicCloudUserDto user) throws Exception;

	int selectMemberInfoYn(@Param("userEmail")String userEmail, @Param("userPw") String userPw) throws Exception;

	List<MusicCloudMusicDto> searchMusic(@Param("keyword")String keyword) throws Exception;

	List<MusicCloudUserDto> userInfoByPk(int userPk) throws Exception;

}
