package com.ssafy.enjoytrip.board.model;

import lombok.Data;

@Data
public class Board {
	private int boardNo;
	private String boardTitle;
	private String memberId;	
	private String boardContent;
	private int boardHit;
	private int boardLove;
	private int boardX;
	private int boardY;
	private int boardImg;
	private String boardRegisterTime;
}
