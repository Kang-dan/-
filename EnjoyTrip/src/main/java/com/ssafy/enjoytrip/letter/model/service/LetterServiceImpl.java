package com.ssafy.enjoytrip.letter.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.letter.model.Letter;
import com.ssafy.enjoytrip.letter.model.mapper.LetterMapper;

@Service
public class LetterServiceImpl implements LetterSerivce {
	
	private final LetterMapper letterMapper;
	public LetterServiceImpl(LetterMapper letterMapper) {
		this.letterMapper = letterMapper;
	}
	@Override
	public void write(Letter letter) {
		letterMapper.letterWrite(letter);
	}
	@Override
	public Letter view(int no) {
		return letterMapper.letterView(no);
	}
	@Override
	public void delete(int no) {
		letterMapper.letterDelete(no);
	}
	@Override
	public List<Letter> letterList(int memberNo) {
		return letterMapper.letterList(memberNo);
	}
	@Override
	public void update(Letter letter) {
		letterMapper.letterUpdate(letter);
	}
	@Override
	public void updateHit(int no) {
		letterMapper.letterUpdateHit(no);
	}
	
}
