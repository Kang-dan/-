package com.ssafy.enjoytrip.letter.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.FileUpload;
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
