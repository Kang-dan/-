package com.ssafy.enjoytrip.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.configuration.model.mapper.ParsingMapper;
import com.ssafy.enjoytrip.member.model.Member;


@CrossOrigin("*")
@RestController
@RequestMapping("/parsing")
public class Parsing {
	
	private final ParsingMapper parsingMapper;
	
	public Parsing(ParsingMapper parsingMapper) {
		this.parsingMapper = parsingMapper;
	}

	@PostMapping
	public ResponseEntity<String> festival(@RequestBody List<Map<String, Object>> params) {
		System.out.println(params.get(0).get("addr1"));		
		
		List<Member> memberList = parsingMapper.test();
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
}
