package com.ssafy.enjoytrip.member.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    
    public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
    
	@Override
    public int idCheck(String memberId) {
        return memberMapper.idCheck(memberId);
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

}