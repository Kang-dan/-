package com.ssafy.enjoytrip.attraction.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionFood;
import com.ssafy.enjoytrip.attraction.model.AttractionList;
import com.ssafy.enjoytrip.attraction.model.AttractionParameter;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private final AttractionMapper attractionMapper;
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}


	@Override
	public List<Attraction> listAttraction(Attraction attraction) {		
		return attractionMapper.listAttraction(attraction);
	}

	@Override
	public AttractionList listAttraction(AttractionParameter attractionParameter) {
		
		return null;
	}


	@Override
	public void insertAttractionFood(AttractionFood food) {
		attractionMapper.insertAttractionFood(food);
	}


	@Override
	public void insertAttractionFestival(AttractionFestival attractionFestival) {
		attractionMapper.insertAttractionFestival(attractionFestival);		
	}
	
}
