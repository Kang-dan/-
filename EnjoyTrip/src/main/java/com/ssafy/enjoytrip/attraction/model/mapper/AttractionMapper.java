package com.ssafy.enjoytrip.attraction.model.mapper;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionFood;

public interface AttractionMapper {
	List<Attraction> listAttraction(Attraction attraction);

	void insertAttractionFood(AttractionFood food);

	void insertAttractionFestival(AttractionFestival attractionFestival);
}
