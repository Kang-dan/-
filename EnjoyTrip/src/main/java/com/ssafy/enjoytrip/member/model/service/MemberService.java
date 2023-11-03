package com.ssafy.enjoytrip.member.model.service;

import com.ssafy.enjoytrip.member.model.Member;

public interface MemberService{
	
	int idCheck(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);

}