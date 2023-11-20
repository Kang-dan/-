package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardSerivce {
	
	private final BoardMapper boardMapper;
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	public void write(Board board) {
		boardMapper.boardInsert(board);		
	}

	@Override
	public Board view(int no) {
		return boardMapper.boardView(no);
	}

	@Override
	public void delete(int no) {
		boardMapper.boardDelete(no);
		
	}
	
	@Override
	public List<Board> list(int sidoCode) {
		return boardMapper.boardList(sidoCode);
	}

	@Override
	public void update(Board board) {
		boardMapper.boardUpdate(board);
		
	}

	@Override
	public void updateHit(int no) {
		boardMapper.boardUpdateHit(no);
	}

	@Override
	public void updateLove(Map<String, Object> map) {
		boardMapper.boardUpdateLove(map);
	}


	
}
