package com.ssafy.enjoytrip.attraction.model;

import java.util.List;

public class AttractionList {
	private List<Attraction> Attractions;
	private int currentPage;
	private int totalPageCount;
	public List<Attraction> getAttractions() {
		return Attractions;
	}
	public void setAttractions(List<Attraction> attractions) {
		Attractions = attractions;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
}
