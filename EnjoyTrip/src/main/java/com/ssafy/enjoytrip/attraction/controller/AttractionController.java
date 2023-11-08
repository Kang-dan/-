package com.ssafy.enjoytrip.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.attraction.model.Attraction;
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

	
}