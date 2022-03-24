package com.bitc.cjh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.cjh.dto.UserDto;
import com.bitc.cjh.mapper.MemberMapper;


@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public void signinUser(UserDto user) throws Exception {
		memberMapper.signinUser(user);
	}

	@Override
	public int selectMemberInfoYn(String userEmail, String userPw) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.selectMemberInfoYn(userEmail, userPw);
	}

	@Override
	public List<UserDto> userInfoByPk(int userPk) throws Exception {
		List<UserDto> users = memberMapper.userInfoByPk(userPk);
		return users;
	}
	
	@Override
	public UserDto selectMember(String userEmail) throws Exception {
		return memberMapper.selectMember(userEmail);
	}
	
	
	
	@Override
	public void register(UserDto memberDto) throws Exception {
		memberMapper.register(memberDto);
	}

	@Override
	public void update(UserDto memberDto) throws Exception {
		memberMapper.update(memberDto);
	}

	@Override
	public void delete(int userPk) throws Exception {
		memberMapper.delete(userPk);
	}

	@Override
	public int emailCheck(String userEmail) throws Exception {
		
		return memberMapper.emailCheck(userEmail);
	}

	@Override
	public UserDto selectMemberInfo(String userEmail) throws Exception {
		return memberMapper.selectMemberInfo(userEmail);
	}

	@Override
	public int findId(UserDto memberDto) throws Exception {
		return memberMapper.findId(memberDto);
	}

	@Override
	public String findGetId(UserDto memberDto) throws Exception {
		return memberMapper.findGetId(memberDto);
	}

	@Override
	public int findPw(UserDto memberDto) throws Exception {
		return memberMapper.findPw(memberDto);
	}

	@Override
	public void updateTempPw(UserDto memberDto) throws Exception {
		memberMapper.updateTempPw(memberDto);
	}

	@Override
	public Object findpwProcess(HashMap<String, Object> hashMap) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		UserDto memberDto = new UserDto();
		String mode = (String) hashMap.get("mode");
		
		if(mode.equals("0")) {

			memberDto.setUserPhone(hashMap.get("userPhone").toString());
			memberDto.setUserName(hashMap.get("userName").toString());
			
			int count = memberMapper.findId(memberDto);
			
			String tempId = memberMapper.findGetId(memberDto);
			
			map.put("count", count);
			map.put("tempId", tempId);
		}
		else if(mode.equals("1")) {
			
		memberDto.setUserEmail(hashMap.get("userEmail").toString());
			
		memberDto.setUserPhone(hashMap.get("userPhone").toString());
			
		int count = memberMapper.findPw(memberDto);
		
		map.put("count", count);
		
		if(count == 1) { 
			Random rnd =new Random();
			StringBuffer buf =new StringBuffer();

			for(int i=0;i<10;i++){
					
			    if(rnd.nextBoolean()){
			    	// 영어 소문자 ASCII(97~122)
			        buf.append((char)((int)(rnd.nextInt(26))+97));
			    }
			    else{
			    	// 숫자
			        buf.append((rnd.nextInt(10)));
			    }
			}
			
			String userPw = buf.toString();
			memberDto.setUserPw(userPw);

			map.put("tempPw", userPw);

			memberMapper.updateTempPw(memberDto);
		} 
		}		
		
		return map;
	}



}
