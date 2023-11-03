package com.ssafy.enjoytrip.food.model.mapper;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.food.model.FoodDto;

public interface FoodMapper {
	List<FoodDto> listFood(Attraction attraction);
}
