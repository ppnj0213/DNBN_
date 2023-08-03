package com.dnbn.back.member.controller;

import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.common.error.exception.BoardException;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDetailDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.service.MemberService;
import com.dnbn.back.security.auth.MemberDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@Description("로그인")
	@GetMapping("/login")
	public ResponseEntity<MemberDetails> login(@AuthenticationPrincipal MemberDetails memberDetails) {
		return ResponseEntity.ok(memberDetails);
	}

	@Description("로그아웃")
	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		return ResponseEntity.ok("로그아웃 success");
	}

	@Description("회원가입")
	@PostMapping("/signup")
	public ResponseEntity<String> createMember(@RequestBody @Valid MemberCreateDto memberCreateDto) {
		memberService.join(memberCreateDto);
		return ResponseEntity.ok("회원가입 success");
	}

	@Description("아이디 중복체크")
	@GetMapping("/signup/{userId}/checkId")
	public ResponseEntity<String> checkUserIdDuplicate(@PathVariable String userId) {
		if (memberService.isUserIdDuplicated(userId)) {
			throw new BoardException(ErrorCode.ID_DUPLICATED);
		}
		return ResponseEntity.ok("사용 가능한 아이디입니다.");
	}

	@Description("닉네임 중복체크")
	@GetMapping("/signup/{nickname}/checkNickname")
	public ResponseEntity<String> checkNicknameDuplicate(@PathVariable String nickname) {
		if (memberService.isNicknameDuplicated(nickname)) {
			throw new BoardException(ErrorCode.NICKNAME_DUPLICATED);
		}
		return ResponseEntity.ok("사용 가능한 닉네임입니다.");
	}

	@Description("내 정보 조회")
	@GetMapping("/{memberId}/mypage")
	public ResponseEntity<MemberDetailDto> getMemberDetail(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMemberDetail(memberId));
	}

	@Description("내 정보 수정")
	@PatchMapping("/{memberId}/mypage")
	public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberUpdateDto memberUpdateDto) {
		memberService.updateMember(memberId, memberUpdateDto);
		return ResponseEntity.ok("회원정보 수정 success");
	}

}
