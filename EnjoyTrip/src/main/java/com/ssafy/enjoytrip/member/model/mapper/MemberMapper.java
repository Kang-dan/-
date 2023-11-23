package com.ssafy.enjoytrip.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.MemberLikes;
import com.ssafy.enjoytrip.member.model.MemberLoves;

@Mapper
public interface MemberMapper {
	
	Member memberInfo(String memberId);
	void registMember(Member member); //회원가입 
	Member loginMember(Member member); //로그인 
	Member detailMember(String memberId);
	void updateMember(Member member);
	void deleteMember(String memberId);
	void deleteMemberLikes(String memberId);
	
	void saveRefreshToken(Map<String, String> map);
	Object getRefreshToken(String userid);
	void deleteRefreshToken(Map<String, String> map);
	List<MemberLikes> memberLikeListOne(Map<String, Object> map);
	void memberLikeInsert(MemberLikes memberLikes);
	void memberLikeDelete(MemberLikes memberLikes);
	List<Map<String, Object>> memberLikeList(Map<String, Object> map);
	
	void insertMemberLoves(MemberLoves memberLoves);
	void deleteMemberLoves(MemberLoves memberLoves);
	void deleteMemberLovesAll(int memberNo);
	List<MemberLoves> selectMemberLoveListOne(MemberLoves memberLoves);
	List<Integer> selectMemberLoveListBoardNo(int memberNo);
	
}