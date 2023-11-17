package com.ssafy.enjoytrip.member.model;

import lombok.Data;

@Data
public class MemberLikes {
	private int no;
	private String memberId;
	private int contentId;
	private int contentFestivalId;
	private String contentTitle;
}
