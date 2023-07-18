package com.dnbn.back.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.member.model.MemberDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDto> getMemberDetail(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMemberDetail(memberId));
	}

	@PutMapping("/{memberId}")
	public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateDto memberUpdateDto) {
		memberService.updateMember(memberId, memberUpdateDto);
		return ResponseEntity.ok("업데이트 success");
	}
}
