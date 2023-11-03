package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.Page;
import com.ssafy.enjoytrip.board.model.PageResult;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

public class BoardServiceImpl implements BoardSerivce {
	private static BoardSerivce boardService = new BoardServiceImpl();
	private BoardMapper boardDao;
	private BoardServiceImpl() {
		boardDao = BoardDaoImpl.getBoardDao();
	}
	
	public static BoardSerivce getBoardService() {
		return boardService;
	}

	@Override
	public List<Board> list(HashMap<String, String> map) throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		String key = map.get("key");
		String word = map.get("word");
		params.put("key", key == null ? "" : key);
		params.put("word", word == null ? "" : word);
		
		return boardDao.list(params);
	}

	@Override
	public void write(Board board) {
		boardDao.write(board);
		
	}

	@Override
	public Board view(int articleNo) {
		return boardDao.view(articleNo);
	}

	@Override
	public void modify(Board board) {
		boardDao.modify(board);
	}

	@Override
	public void delete(int articleNo) {
		boardDao.delete(articleNo);
		
	}

	@Override
	public Map<String, Object> listBoard1(Page page) throws Exception {
		List<Board> list = boardDao.selectBoard(page);
		int count = boardDao.selectBoardCount();
		PageResult pg = new PageResult(page.getPageNo(), count);
		
		Map<String, Object> result = new HashMap<>();
		for(int i = 0; i < list.size(); i++) {
		}
		result.put("list", list);
		result.put("pg", pg);
		return result;
	}



	
}
