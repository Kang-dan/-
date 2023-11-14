package com.ssafy.enjoytrip.attraction.model;

import lombok.Data;

@Data
public class AttractionFestival {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String tel;
	private String firstImage;
	private String sidoCode;
	private String gugunCode;
	private String latitude;
	private String longitude;
	private String mlevel;
	private String eventStartDate;
	private String eventEndDate;
}
