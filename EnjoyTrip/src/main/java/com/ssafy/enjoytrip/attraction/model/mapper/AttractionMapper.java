package com.ssafy.enjoytrip.attraction.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionFood;

@Mapper
public interface AttractionMapper {
	List<Attraction> listAttraction(Attraction attraction);

	void insertAttractionFood(AttractionFood food);

	void insertAttractionFestival(AttractionFestival attractionFestival);
}
