package com.ssafy.enjoytrip.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.FileUpload;
import com.ssafy.enjoytrip.board.model.service.BoardSerivce;
import com.ssafy.enjoytrip.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping("/board")
public class BoardController {
	private final BoardSerivce boardSerivce;
	private final FileUtils fileUtils;
	public BoardController(BoardSerivce boardSerivce) {
		this.fileUtils = new FileUtils();
		this.boardSerivce = boardSerivce;
	}

	@GetMapping("/list")
	public ResponseEntity<List<Board>> list() {
		return ResponseEntity.ok(boardSerivce.list());
	}
	
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody Board board) {
		boardSerivce.write(board);
		List<FileUpload> files = fileUtils.uploadFiles(board.getFiles());
		boardSerivce.saveFiles(board.getBoardNo(), files);
		return ResponseEntity.ok("글쓰기 성공");
	}	

	@GetMapping("/view/{no}")
	public ResponseEntity<Board> view(@PathVariable int no) {
		return ResponseEntity.ok(boardSerivce.view(no));
	}
	
	@DeleteMapping("/delete/{no}")
	public ResponseEntity<String> delete(@PathVariable int no) {
		boardSerivce.delete(no);
		return ResponseEntity.ok("글삭제 성공");
	}
	
	@DeleteMapping("/{memberId}")
	public ResponseEntity<String> deleteMemberBoards(@PathVariable String memberId) {
		boardSerivce.deleteMemberBoards(memberId);
		return ResponseEntity.ok("글삭제 성공");
	} 
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Board board) {
		boardSerivce.update(board);
		return ResponseEntity.ok("글수정 성공");
	}
	
	@GetMapping("/update/hit/{no}")
	public ResponseEntity<String> updateHit(@PathVariable int no) {
		boardSerivce.updateHit(no);
		return ResponseEntity.ok("조회수 + 1 성공");
	}
	
	@PostMapping("/update/love")
	public ResponseEntity<String> updateLove(@RequestBody Map<String, Object> map) {
		boardSerivce.updateLove(map);
		return ResponseEntity.ok("좋아요 변경 성공");
	}

}

