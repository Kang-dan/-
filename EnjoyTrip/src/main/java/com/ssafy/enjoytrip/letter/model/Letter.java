package com.ssafy.enjoytrip.letter.model;

import lombok.Data;

@Data
public class Letter {
	private int letterNo;
	private String letterTitle;
	private int memberNoFrom;
	private int memberNoTo;
	private String memberIdFrom;
	private String memberIdTo;
	private int letterX;
	private int letterY;
	private int letterImg;
	private String letterContent;
	private int letterHit;
	private String letterRegisterTime;
    
}
