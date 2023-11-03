package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Page;
import com.ssafy.enjoytrip.board.model.PageResult;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardSerivce {
	
	private final BoardMapper boardMapper;
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
//	@Override
//	public List<Board> list(HashMap<String, String> map) throws Exception {
//		HashMap<String, Object> params = new HashMap<>();
//		String key = map.get("key");
//		String word = map.get("word");
//		params.put("key", key == null ? "" : key);
//		params.put("word", word == null ? "" : word);
//		
//		return boardDao.list(params);
//	}

	public void write(Board board) {
		boardMapper.insertBoard(board);
		
	}

	@Override
	public Board view(int no) {
		return boardMapper.selectBoard(no);
	}

	@Override
	public void modify(Board board) {
		boardMapper.updateBoard(board);
	}

	@Override
	public void delete(int no) {
		boardMapper.deleteBoard(no);
		
	}

	//페이지??
	@Override
	public Map<String, Object> listBoard1(Page page) throws Exception {
		List<Board> list = boardMapper.selectBoard(page);
		int count = boardMapper.selectBoardCount();
		PageResult pg = new PageResult(page.getPageNo(), count);
		
		Map<String, Object> result = new HashMap<>();
		for(int i = 0; i < list.size(); i++) {
		}
		result.put("list", list);
		result.put("pg", pg);
		return result;
	}

	@Override
	public List<Board> list() {
		return boardMapper.selectBoard();
	}



	
}
