package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;

public class MemberServiceImpl implements MemberService {
    private static MemberService memberService = new MemberServiceImpl();
    private MemberMapper memberDao;
    
    private MemberServiceImpl() {
        memberDao = MemberDaoImpl.getInstance();
    }
    
    public static MemberService getMemberService() {
        return memberService;
    }
    
    @Override
    public int idCheck(String userId) throws SQLException {
        return 0;
    }
    @Override
    public void regist(Member member) throws SQLException {
        member.setUserPwd(BCrypt.hashpw(member.getUserPwd(), BCrypt.gensalt()));
        memberDao.regist(member);
    }
    @Override
    public Member loginMember(Member member) throws SQLException {
        return memberDao.loginMember(member);
    }

}