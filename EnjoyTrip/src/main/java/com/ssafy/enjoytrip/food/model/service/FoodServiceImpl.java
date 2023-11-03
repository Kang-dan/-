package com.ssafy.enjoytrip.food.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.food.model.FoodDto;
import com.ssafy.enjoytrip.food.model.mapper.FoodMapper;

@Service
public class FoodServiceImpl implements FoodService {
	
	private final FoodMapper foodMapper;
	public FoodServiceImpl(FoodMapper foodMapper) {
		super();
		this.foodMapper = foodMapper;
	}
	
	
	
//	private static FoodService foodService = new FoodServiceImpl();
//	private FoodMapper foodDao;
//	
//	private FoodServiceImpl() {
//		foodDao = FoodDaoImpl.getInstance();
//	}
//	public static FoodService getFoodService() {
//		return foodService;
//	}
//
//	
	@Override
	public List<FoodDto> listFood(int areaCode, int foodCode) throws Exception {
		return foodMapper.listFood(areaCode, foodCode);
	}
	
}
