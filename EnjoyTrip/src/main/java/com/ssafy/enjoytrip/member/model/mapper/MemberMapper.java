package com.ssafy.enjoytrip.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.Member;

@Mapper
public interface MemberMapper {
	
	int idCheck(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);
	void deleteMember(String memberId);
}