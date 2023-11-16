package com.ssafy.enjoytrip.attraction.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;

public interface AttractionService {
	void insertAttractionFestival(AttractionFestival attractionFestival);
	List<AttractionFestival> listAttractionFestival(int sidoCode);
	Collection<? extends AttractionFestival> listAttractionFood(int sidoCode);
	Collection<? extends AttractionFestival> listAttractionFamily(int sidoCode);
	AttractionFestival detailAttraction(Map<String, Object> map);
}
