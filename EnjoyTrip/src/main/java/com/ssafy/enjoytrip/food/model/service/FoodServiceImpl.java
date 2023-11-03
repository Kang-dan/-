package com.ssafy.enjoytrip.food.model.service;

import java.util.List;

import com.ssafy.enjoytrip.food.model.FoodDto;
import com.ssafy.enjoytrip.food.model.mapper.FoodMapper;
import com.ssafy.enjoytrip.food.model.mapper.FoodMapper;

public class FoodServiceImpl implements FoodService {
	private static FoodService foodService = new FoodServiceImpl();
	private FoodMapper foodDao;
	
	private FoodServiceImpl() {
		foodDao = FoodDaoImpl.getInstance();
	}
	public static FoodService getFoodService() {
		return foodService;
	}

	
	@Override
	public List<FoodDto> listFood(int areaCode, int foodCode) throws Exception {
		return foodDao.listFood(areaCode, foodCode);
	} 
	
}
