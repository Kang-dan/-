package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.FileUpload;

public interface BoardSerivce {

	void write(Board board); 

	Board view(int no);

	void delete(int no);
	
	List<Board> list();

	void update(Board board);

	void updateHit(int no);

	void updateLove(Map<String, Object> map);

	void deleteMemberBoards(String memberId);
	
	
	void saveFiles(int boardNo, List<FileUpload> files);
}
