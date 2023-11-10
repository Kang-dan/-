package com.ssafy.enjoytrip.member.model;

import lombok.Data;

@Data
public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberEmail;
	private int age;
	private String joinDate;
	private int memberBoardCount;
	
}
