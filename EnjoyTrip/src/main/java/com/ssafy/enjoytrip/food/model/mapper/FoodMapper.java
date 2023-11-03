package com.ssafy.enjoytrip.food.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.food.model.FoodDto;

public interface FoodMapper {
	List<FoodDto> listFood(int areaCode, int foodCode) throws SQLException;
}
