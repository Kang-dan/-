package com.ssafy.enjoytrip.board.model;

import lombok.Data;

@Data
public class Board {
	private int no;
	private String title;
	private String memberId;
	private String content;
	private int hit;
	private String registerTime;
}
