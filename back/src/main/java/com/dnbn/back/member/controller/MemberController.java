package com.dnbn.back.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDetailDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.ok("로그인 success");
	}

	// @PostMapping("/login")
	// public ResponseEntity<String> loginSuccess(MemberLoginDto memberLoginDto) {
	// 	return ResponseEntity.ok("로그인 success");
	// }

	@PostMapping("/signup")
	public ResponseEntity<?> createMember(@RequestBody MemberCreateDto memberCreateDto) {
		memberService.join(memberCreateDto);
		return ResponseEntity.ok("회원가입 success");
	}

	@GetMapping("/signup/checkId/{userId}")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
		return ResponseEntity.ok(memberService.isUserIdDuplicated(userId));
	}

	@GetMapping("/signup/checkNickname/{nickname}")
	public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
		return ResponseEntity.ok(memberService.isNicknameDuplicated(nickname));
	}

	@GetMapping("/mypage/{memberId}")
	public ResponseEntity<MemberDetailDto> getMemberDetail(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMemberDetail(memberId));
	}

	@PutMapping("/mypage/{memberId}")
	public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateDto memberUpdateDto) {
		memberService.updateMember(memberId, memberUpdateDto);
		return ResponseEntity.ok("회원정보 수정 success");
	}

}
