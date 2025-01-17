package com.ssafy.enjoytrip.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ssafy.enjoytrip.member.model.MemberLikes;
import com.ssafy.enjoytrip.member.model.MemberLoves;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.util.JWTUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	private JWTUtil jwtUtil;

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/love/list/{memberNo}")
	public ResponseEntity<List<Integer>> loveList(@PathVariable int memberNo) {
		return ResponseEntity.ok(memberService.memberLoveListBoardNo(memberNo));
	}
	
	@PostMapping("/love/list")
	public ResponseEntity<List<MemberLoves>> loveListOne(@RequestBody MemberLoves memberLoves) {
		System.out.println(memberLoves);
		return ResponseEntity.ok(memberService.memberLoveListOne(memberLoves));
	}
	
	@PostMapping("/love/insert")
	public ResponseEntity<String> loveInsert(@RequestBody MemberLoves memberLoves) {
		System.out.println(memberLoves);
		memberService.memberLoveInsert(memberLoves);
		return ResponseEntity.ok("좋아요 추가 성공");
	}
	
	@PostMapping("/love/delete")
	public ResponseEntity<String> loveDelete(@RequestBody MemberLoves memberLoves) {
		System.out.println(memberLoves);
		memberService.memberLoveDelete(memberLoves);
		return ResponseEntity.ok("좋아요 삭제 성공");
	}
	
	@DeleteMapping("/love/delete/{memberNo}")
	public ResponseEntity<String> loveDeleteAll(@PathVariable int memberNo) {
		System.out.println(memberNo);
		memberService.memberLoveDeleteAll(memberNo);
		return ResponseEntity.ok("해당 ID 좋아요 전부 삭제 성공");
	}
	
	@PostMapping("/like")
	public ResponseEntity<List<?>> likeList(@RequestBody Map<String, Object> map) {
		if (map.get("contentId") == null && map.get("contentFestivalId") == null) {			
			return ResponseEntity.ok(memberService.memberLikeList(map));			
		}
		else return ResponseEntity.ok(memberService.memberLikeListOne(map));
	}
	
	@PostMapping("/like/insert")
	public ResponseEntity<String> likeInsert(@RequestBody MemberLikes memberLikes) {
		memberService.memberLikeInsert(memberLikes);
		return ResponseEntity.ok("성공");
	}
	
	@PostMapping("/like/delete")
	public ResponseEntity<String> likeDelete(@RequestBody MemberLikes memberLikes) {
		memberService.memberLikeDelete(memberLikes);
		return ResponseEntity.ok("성공");
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
	
	@PostMapping("/idCheck")
	public ResponseEntity<String> idCheck(@RequestBody Member member) {
		member = memberService.loginMember(member);
		if (member != null) {
			return ResponseEntity.ok("성공");
		}
		else {
			return ResponseEntity.ok("실패");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Member member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			member = memberService.loginMember(member);						
			if (member != null) {
				String accessToken = jwtUtil.createAccessToken(member.getMemberId());
				String refreshToken = jwtUtil.createRefreshToken(member.getMemberId());
				System.out.println("accessToken : " + accessToken);
				System.out.println("refreshToken : " + refreshToken);
				
				//발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(member.getMemberId(), refreshToken);
				
				//JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				
				// Cookie로 변경 	
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} 
			else {				
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;				
			}
		} catch (Exception e) {
			System.out.println("로그인 에러 발생 : " + e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{memberId}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable String memberId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			System.out.println("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				Member member = memberService.memberInfo(memberId);
				resultMap.put("memberInfo", member);
				status = HttpStatus.OK;
			} catch (Exception e) {
				System.out.println("정보조회 실패 : " + e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			System.out.println("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/logout/{memberId}")
	public ResponseEntity<?> logout(@PathVariable String memberId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;		
		try {
			memberService.deleRefreshToken(memberId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println("로그아웃 실패 : " + e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Member member) {
		memberService.updateMember(member);
		return ResponseEntity.ok("OK");// 201번 보냄
	}
	
	@DeleteMapping("{memberId}")
	public ResponseEntity<String> delete(@PathVariable String memberId) {
		System.out.println("삭제요청");
		memberService.deleteMemberLikes(memberId);
		memberService.deleteMember(memberId);
		return ResponseEntity.ok("OK");// 201번 보냄
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Member member, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		System.out.println("token : {" + token + "}, memberDto : {" + member + "}");
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(member.getMemberId()))) {
				String accessToken = jwtUtil.createAccessToken(member.getMemberId());
				System.out.println("token : " + accessToken);
				System.out.println("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			System.out.println("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
}
