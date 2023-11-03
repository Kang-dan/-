package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Page;

public interface BoardSerivce {

	void write(Board board);

	Board view(int articleNo);

	void modify(Board board);

	void delete(int articleNo);
	
	List<Board> list(HashMap<String, String> map) throws Exception;
	
	Map<String, Object> listBoard1(Page page) throws Exception;
}
