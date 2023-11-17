package com.ssafy.enjoytrip.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.MemberLikes;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    
    public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
    
	@Override
    public Member memberInfo(String memberId) {
        return memberMapper.memberInfo(memberId);
    }
    @Override
    public void registMember(Member member) {        
        memberMapper.registMember(member);
    }
    @Override
    public Member loginMember(Member member) {
        return memberMapper.loginMember(member);
    }

	@Override
	public Member detailMember(String memberId) {
		return memberMapper.detailMember(memberId);
	}

	@Override
	public void updateMember(Member member) {
		memberMapper.updateMember(member);
		
	}

	@Override
	public void deleteMember(String memberId) {
		memberMapper.deleteMember(memberId);		
	}

	@Override
	public void saveRefreshToken(String memberId, String refreshToken) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);		
	}

	@Override
	public Object getRefreshToken(String memberId) {
		return memberMapper.getRefreshToken(memberId);
	}

	@Override
	public void deleRefreshToken(String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

	@Override
	public List<MemberLikes> memberLikeList(String memberId) {
		return memberMapper.memberLikeList(memberId);
	}

	@Override
	public void memberLikeInsert(MemberLikes memberLikes) {
		memberMapper.memberLikeInsert(memberLikes);		
	}

	@Override
	public void memberLikeDelete(MemberLikes memberLikes) {
		memberMapper.memberLikeDelete(memberLikes);
	}

}