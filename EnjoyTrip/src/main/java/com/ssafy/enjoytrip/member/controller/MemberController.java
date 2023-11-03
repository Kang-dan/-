package com.ssafy.enjoytrip.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.model.Member;
import com.ssafy.enjoytrip.member.model.service.MemberService;

@CrossOrigin("*")
@RestController
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/detail/{memberId}")
	public ResponseEntity<Member> detail(@PathVariable String memberId) {
		return ResponseEntity.ok(memberService.detailMember(memberId));
	}

	@PostMapping("/regist")
	public ResponseEntity<String> regist(@RequestBody Member member) {
		memberService.registMember(member);
		return ResponseEntity.status(HttpStatus.CREATED).build(); // 201번 보냄
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Member member, HttpSession session) {
		member = memberService.loginMember(member);
		if (member != null) {
			session.setAttribute("memberInfo", member);
			return ResponseEntity.ok("로그인 성공!");// 201번 보냄
		}
		return ResponseEntity.ok("로그인 실패");// 201번 보냄
	}
	
	@GetMapping("/idCheck/{memberId}")
	public ResponseEntity<String> idCheck(@PathVariable String memberId) {
		int cnt = memberService.idCheck(memberId);
		return ResponseEntity.ok("OK");// 201번 보냄 
	}
	
	@PutMapping("{memberId}")
	public ResponseEntity<String> update(@PathVariable String memberId, @RequestBody Member member) {
		memberService.updateMember(member);
		return ResponseEntity.ok("OK");// 201번 보냄
	}
	
	@DeleteMapping("{memberId}")
	public ResponseEntity<String> delete(@PathVariable String memberId) {
		memberService.deleteMember(memberId);
		return ResponseEntity.ok("OK");// 201번 보냄
	}

	
}
