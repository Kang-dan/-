package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Page;

public interface BoardSerivce {

	void write(Board board); 

	Board view(int no);

	void modify(Board board);

	void delete(int no);
	
	List<Board> list();
	
	Map<String, Object> listBoard1(Page page);
}
