package com.ssafy.enjoytrip.letter.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.letter.model.Letter;

@Mapper
public interface LetterMapper {

	void letterWrite(Letter letter);

	Letter letterView(int no);

	void letterDelete(int no);

	List<Letter> letterList(int memberNo);

	void letterUpdate(Letter letter);

	void letterUpdateHit(int no);

	

}
