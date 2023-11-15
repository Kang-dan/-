package com.ssafy.enjoytrip.member.model.service;

import com.ssafy.enjoytrip.member.model.Member;

public interface MemberService{
	
	Member memberInfo(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);
	void deleteMember(String memberId);
	
	void saveRefreshToken(String memberId, String refreshToken);
	Object getRefreshToken(String memberId);
	void deleRefreshToken(String memberId);

}