package com.ssafy.enjoytrip.attraction.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionOverview;

public interface AttractionService {
	void insertAttractionFestival(AttractionFestival attractionFestival);
	List<AttractionFestival> listAttractionFestival(Map<String, Object> map);
	Collection<? extends AttractionFestival> listAttractionFood(Map<String, Object> map);
	Collection<? extends AttractionFestival> listAttractionFamily(Map<String, Object> map);
	AttractionOverview detailAttraction(Map<String, Object> map);
}
