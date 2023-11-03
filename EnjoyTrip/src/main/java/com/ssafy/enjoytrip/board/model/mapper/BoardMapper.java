package com.ssafy.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Page;

public interface BoardMapper {

	void write(Board board);

	Board view(int articleNo);

	void modify(Board board);

	void delete(int articleNo);

	List<Board> list(HashMap<String, Object> params) throws SQLException;
	
	public List<Board> selectBoard(Page search) throws SQLException;
	public int selectBoardCount() throws SQLException;
}
