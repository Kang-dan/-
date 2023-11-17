package com.ssafy.enjoytrip.attraction.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionOverview;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private final AttractionMapper attractionMapper;
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionFestival> listAttractionFestival(Map<String, Object> map) {
		return attractionMapper.listAttractionFestival(map);
	}

	@Override
	public void insertAttractionFestival(AttractionFestival attractionFestival) {
		attractionMapper.insertAttractionFestival(attractionFestival);		
	}


	@Override
	public Collection<? extends AttractionFestival> listAttractionFood(Map<String, Object> map) {
		return attractionMapper.listAttractionFood(map);
	}


	@Override
	public Collection<? extends AttractionFestival> listAttractionFamily(Map<String, Object> map) {
		return attractionMapper.listAttractionFamily(map);
	}

	@Override
	public AttractionOverview detailAttraction(Map<String, Object> map) {
		return attractionMapper.detailAttraction(map);
	}

	
}
