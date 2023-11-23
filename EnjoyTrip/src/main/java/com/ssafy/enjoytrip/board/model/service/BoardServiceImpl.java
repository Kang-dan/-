package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.FileUpload;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", board.getMemberId());
		map.put("num", 1);
		boardMapper.boardCountUpdate(map);
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
	public List<Board> list() {
		return boardMapper.boardList();
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

	@Override
	public void deleteMemberBoards(String memberId) {
		boardMapper.deleteMemberBoards(memberId);
		
	}
	
	@Override
	public void saveFiles(int boardNo, List<FileUpload> files) {
		if (CollectionUtils.isEmpty(files)) return;
		for (FileUpload file : files) {
			file.setBoardNo(boardNo);
		}
		boardMapper.saveAll(files);
	}

	@Override
	public void updateLoveAll(List<Integer> boardNo) {
		boardMapper.boardUpdateLoveAll(boardNo);
	}

	
}
