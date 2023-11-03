package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;

import com.ssafy.enjoytrip.member.model.Member;

public interface MemberMapper {
	
	int idCheck(String userId) throws SQLException;
	void regist(Member memberDto) throws SQLException; //회원가입 
	Member loginMember(Member member) throws SQLException; //로그인 
}