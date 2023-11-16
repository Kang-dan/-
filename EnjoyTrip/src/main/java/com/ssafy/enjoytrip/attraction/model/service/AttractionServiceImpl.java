package com.ssafy.enjoytrip.attraction.model.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private final AttractionMapper attractionMapper;
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionFestival> listAttractionFestival(int sidoCode) {
		return attractionMapper.listAttractionFestival(sidoCode);
	}

	@Override
	public void insertAttractionFestival(AttractionFestival attractionFestival) {
		attractionMapper.insertAttractionFestival(attractionFestival);		
	}


	@Override
	public Collection<? extends AttractionFestival> listAttractionFood(int sidoCode) {
		return attractionMapper.listAttractionFood(sidoCode);
	}


	@Override
	public Collection<? extends AttractionFestival> listAttractionFamily(int sidoCode) {
		return attractionMapper.listAttractionFamily(sidoCode);
	}

	
}
