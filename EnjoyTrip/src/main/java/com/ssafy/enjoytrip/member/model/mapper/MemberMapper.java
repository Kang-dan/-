package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.Member;

@Mapper
public interface MemberMapper {
	
	Member memberInfo(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);
	void deleteMember(String memberId);
	
	void saveRefreshToken(Map<String, String> map);
	Object getRefreshToken(String userid);
	void deleteRefreshToken(Map<String, String> map);
}