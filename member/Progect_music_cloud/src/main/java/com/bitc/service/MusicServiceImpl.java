package com.bitc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.dto.MusicReplyDto;
import com.bitc.dto.UsersDto;
import com.bitc.mapper.MusicMapper;

@Service
public class MusicServiceImpl implements MusicService{

	@Autowired
	MusicMapper musicMapper;
	
	@Override
	public void register(UsersDto memberDto) throws Exception {
		musicMapper.register(memberDto);
	}

	@Override
	public void update(UsersDto memberDto) throws Exception {
		musicMapper.update(memberDto);
	}

	@Override
	public void delete(int userPk) throws Exception {
		musicMapper.delete(userPk);
	}

	@Override
	public int emailCheck(String userEmail) throws Exception {
		
		return musicMapper.emailCheck(userEmail);
	}

	@Override
	public UsersDto selectMemberInfo(int userPk) throws Exception {
		return musicMapper.selectMemberInfo(userPk);
	}

	@Override
	public int findId(UsersDto memberDto) throws Exception {
		return musicMapper.findId(memberDto);
	}

	@Override
	public String findGetId(UsersDto memberDto) throws Exception {
		return musicMapper.findGetId(memberDto);
	}

	@Override
	public int findPw(UsersDto memberDto) throws Exception {
		return musicMapper.findPw(memberDto);
	}

	@Override
	public void updateTempPw(UsersDto memberDto) throws Exception {
		musicMapper.updateTempPw(memberDto);
	}

	@Override
	public Object findpwProcess(HashMap<String, Object> hashMap) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		UsersDto memberDto = new UsersDto();
		String mode = (String) hashMap.get("mode");
		
		if(mode.equals("0")) {

			memberDto.setUserPhone(hashMap.get("userPhone").toString());
			memberDto.setUserName(hashMap.get("userName").toString());
			
			int count = musicMapper.findId(memberDto);
			
			String tempId = musicMapper.findGetId(memberDto);
			
			map.put("count", count);
			map.put("tempId", tempId);
		}
		else if(mode.equals("1")) {
			
		memberDto.setUserEmail(hashMap.get("userEmail").toString());
			
		memberDto.setUserPhone(hashMap.get("userPhone").toString());
			
		int count = musicMapper.findPw(memberDto);
		
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

			musicMapper.updateTempPw(memberDto);
		} 
		}		
		
		return map;
	}

	@Override
	public List<MusicReplyDto> reply(int userPk) throws Exception {
		return musicMapper.reply(userPk);
	}

	
}
