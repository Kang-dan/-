package com.ssafy.enjoytrip.food.model.service;

import java.util.List;

import com.ssafy.enjoytrip.food.model.FoodDto;

public interface FoodService {
	List<FoodDto> listFood(int areaCode, int foodCode) throws Exception;
}
