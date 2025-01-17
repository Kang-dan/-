package com.ssafy.enjoytrip.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.Board;

@Mapper
public interface BoardMapper {

	void boardInsert(Board board);

	Board boardView(int boardNo);

	void boardUpdate(Board board);

	void boardDelete(int boardNo);
	
	List<Board> boardList();

	void boardUpdateHit(int boardNo);

	void boardUpdateLove(Map<String, Object> map);

	void deleteMemberBoards(String memberId);

	void boardCountUpdate(Map<String, Object> map);

	void boardUpdateLoveAll(List<Integer> boardNo);

}
