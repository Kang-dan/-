package com.ssafy.enjoytrip.attraction.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionList;
import com.ssafy.enjoytrip.attraction.model.AttractionParameter;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/attraction")
public class AttractionController {
	
	private final AttractionService attractionService;
	public AttractionController(AttractionService attractionService) {
		this.attractionService = attractionService;
	}
	
	@PostMapping
	public ResponseEntity<List<Attraction>> list(@RequestBody Attraction attraction) {
		return ResponseEntity.ok(attractionService.listAttraction(attraction));
	}
	
//	@GetMapping
//	public ResponseEntity<?> listAttraction(@RequestParam AttractionParameter attractionParameter) {
//		AttractionList attractionList = attractionService.listAttraction(attractionParameter);
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return ResponseEntity.ok().headers(header).body(attractionList);
//	}

	
}