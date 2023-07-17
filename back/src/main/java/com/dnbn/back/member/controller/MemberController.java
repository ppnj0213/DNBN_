package com.dnbn.back.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberLoginDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.service.MemberService;
import com.dnbn.back.security.auth.MemberDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<?> join(@RequestBody MemberCreateDto memberCreateDto) {
		memberService.join(memberCreateDto);
		return ResponseEntity.ok("회원가입 success");
	}

	@GetMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.ok("로그인 해주떼여");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginSuccess(MemberLoginDto memberLoginDto) {
		return ResponseEntity.ok("로그인 success");
	}

	@GetMapping("/signup/{userId}/exists")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
		return ResponseEntity.ok(memberService.checkUserId(userId));
	}

	@GetMapping("/signup/{nickname}/exists")
	public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
		return ResponseEntity.ok(memberService.checkNickname(nickname));
	}

}
