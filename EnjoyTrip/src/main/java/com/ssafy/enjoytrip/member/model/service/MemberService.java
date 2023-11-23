package com.ssafy.enjoytrip.member.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.MemberLikes;
import com.ssafy.enjoytrip.member.model.MemberLoves;

public interface MemberService{
	
	Member memberInfo(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);
	void deleteMember(String memberId);
	void deleteMemberLikes(String memberId);
	
	void saveRefreshToken(String memberId, String refreshToken);
	Object getRefreshToken(String memberId);
	void deleRefreshToken(String memberId);
	List<MemberLikes> memberLikeListOne(Map<String, Object> map);
	void memberLikeInsert(MemberLikes memberLikes);
	void memberLikeDelete(MemberLikes memberLikes);
	List<Map<String, Object>> memberLikeList(Map<String, Object> map);
	void memberLoveInsert(MemberLoves memberLoves);
	void memberLoveDelete(MemberLoves memberLoves);
	void memberLoveDeleteAll(int memberNo);
	List<MemberLoves> memberLoveListOne(MemberLoves memberLoves);
	List<Integer> selectMemberLoveListBoardNo(int memberNo);
	

}