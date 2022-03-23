package com.bitc.cjh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.cjh.dto.UserDto;


@Mapper
public interface MemberMapper {

	void signinUser(UserDto user) throws Exception;

	int selectMemberInfoYn(@Param("userEmail")String userEmail, @Param("userPw") String userPw) throws Exception;

	List<UserDto> userInfoByPk(int userPk) throws Exception;
	
	UserDto selectMember(String userEmail) throws Exception;
	
	void register(UserDto memberDto) throws Exception;

	void update(UserDto memberDto) throws Exception;

	void delete(@Param("userPk") int userPk) throws Exception;

	int emailCheck(@Param("userEmail") String userEmail) throws Exception;

	UserDto selectMemberInfo(@Param("userPk") int userPk) throws Exception;

	int findId(UserDto memberDto) throws Exception;

	String findGetId(UserDto memberDto) throws Exception;

	int findPw(UserDto memberDto) throws Exception;

	void updateTempPw(UserDto memberDto) throws Exception;

	
	
}
