package com.dnbn.back.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.service.MemberService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Profile("local")
@RequiredArgsConstructor
@Component
public class TestData {

	private final InitMemberService initMemberService;

	@PostConstruct
	public void init() {
		initMemberService.init();
	}

	@Description("멤버 테스트 데이터 생성")
	@Component
	static class InitMemberService {
		@PersistenceContext
		EntityManager em;
		@Autowired MemberService memberService;

		@Transactional
		public void init() {
			// MemberCreateDto memberCreateDto = MemberCreateDto.builder()
			// 	.userId("junyeobk")
			// 	.userPw("1234")
			// 	.nickname("주비")
			// 	.build();
			// memberService.join(memberCreateDto);
		}
	}
}
