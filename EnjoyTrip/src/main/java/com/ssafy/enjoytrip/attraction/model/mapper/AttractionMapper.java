package com.ssafy.enjoytrip.attraction.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;

@Mapper
public interface AttractionMapper {

	void insertAttractionFestival(AttractionFestival attractionFestival);

	List<AttractionFestival> listAttractionFestival(int sidoCode);

	Collection<? extends AttractionFestival> listAttractionFood(int sidoCode);

	Collection<? extends AttractionFestival> listAttractionFamily(int sidoCode);

	AttractionFestival detailAttraction(Map<String, Object> map);
}
