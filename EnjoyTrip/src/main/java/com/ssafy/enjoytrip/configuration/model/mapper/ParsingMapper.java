package com.ssafy.enjoytrip.configuration.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.Member;

@Mapper
public interface ParsingMapper {
	List<Member> test();
}
