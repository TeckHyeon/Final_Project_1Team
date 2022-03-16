package com.team1.musiccloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.team1.musiccloud.dto.MusicCloudUserDto;

@Mapper
public interface MusicCloudMapper {

	void signinUser(MusicCloudUserDto user) throws Exception;

	int selectMemberInfoYn(@Param("userEmail")String userEmail, @Param("userPw") String userPw) throws Exception;

}
