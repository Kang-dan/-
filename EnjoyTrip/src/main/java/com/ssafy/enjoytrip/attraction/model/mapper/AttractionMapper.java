package com.ssafy.enjoytrip.attraction.model.mapper;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.Attraction;

public interface AttractionMapper {
	List<Attraction> listAttraction(Attraction attraction);
}
