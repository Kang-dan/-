package com.ssafy.enjoytrip.letter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.configuration.WebSocketHandler;
import com.ssafy.enjoytrip.letter.model.Letter;
import com.ssafy.enjoytrip.letter.model.service.LetterSerivce;

@CrossOrigin("*")
@RestController
@RequestMapping("/letter")
public class LetterController {
	private final LetterSerivce letterSerivce;
	private final WebSocketHandler webSocketHandler;
	public LetterController(LetterSerivce letterSerivce, WebSocketHandler webSocketHandler) {
		this.letterSerivce = letterSerivce;
		this.webSocketHandler = webSocketHandler;
	}

	@GetMapping("/list/{memberNo}")
	public ResponseEntity<List<Letter>> list(@PathVariable int memberNo) {
		return ResponseEntity.ok(letterSerivce.letterList(memberNo));
	}
	
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody Letter letter) {
		letterSerivce.write(letter);
		webSocketHandler.changeMessage();
		return ResponseEntity.ok("편지쓰기 성공");
	}	

	@GetMapping("/view/{no}")
	public ResponseEntity<Letter> view(@PathVariable int no) {
		return ResponseEntity.ok(letterSerivce.view(no));
	}
	
	@DeleteMapping("/delete/{no}")
	public ResponseEntity<String> delete(@PathVariable int no) {
		System.out.println("편지삭제요청");
		letterSerivce.delete(no);
		webSocketHandler.changeMessage();
		return ResponseEntity.ok("편지삭제 성공");
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Letter letter) {
		letterSerivce.update(letter);
		return ResponseEntity.ok("편지수정 성공");
	}
	
	@GetMapping("/update/hit/{no}")
	public ResponseEntity<String> updateHit(@PathVariable int no) {
		letterSerivce.updateHit(no);
		return ResponseEntity.ok("조회수 + 1 성공");
	}

}

