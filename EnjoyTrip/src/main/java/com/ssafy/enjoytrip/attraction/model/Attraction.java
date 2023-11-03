package com.ssafy.enjoytrip.attraction.model;

import lombok.Data;

@Data
public class Attraction {
	private int contentTypeId;
	private String title;
	private String addr1;
	private String firstImage;
	private String latitude;
	private String longitude;
	private int sidoCode;
	
}
