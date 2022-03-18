package com.bitc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.dto.MusicReplyDto;
import com.bitc.dto.UsersDto;

@Mapper
public interface MusicMapper {

	void register(UsersDto memberDto) throws Exception;

	void update(UsersDto memberDto) throws Exception;

	void delete(@Param("userPk") int userPk) throws Exception;

	int emailCheck(@Param("userEmail") String userEmail) throws Exception;

	UsersDto selectMemberInfo(@Param("userPk") int userPk) throws Exception;

	int findId(UsersDto memberDto) throws Exception;

	String findGetId(UsersDto memberDto) throws Exception;

	int findPw(UsersDto memberDto) throws Exception;

	void updateTempPw(UsersDto memberDto) throws Exception;

	List<MusicReplyDto> reply(@Param("userPk") int userPk) throws Exception;

}
