package com.ssafy.enjoytrip.letter.model.service;

import java.util.List;

import com.ssafy.enjoytrip.letter.model.Letter;

public interface LetterSerivce {

	void write(Letter letter); 

	Letter view(int no);

	void delete(int no);
	
	List<Letter> letterList(int memberNo);

	void update(Letter letter);

	void updateHit(int no);	
	
}
