package com.ssafy.enjoytrip.attraction.model;

import lombok.Data;

@Data
public class Attraction {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String tel;
	private String firstImage;
	private int sidoCode;
	private int gugunCode;
	private String latitude;
	private String longitude;
}
