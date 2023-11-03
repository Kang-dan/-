package com.ssafy.enjoytrip.food.model.service;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.food.model.FoodDto;

public interface FoodService {
	List<FoodDto> listFood(Attraction attraction);
}
