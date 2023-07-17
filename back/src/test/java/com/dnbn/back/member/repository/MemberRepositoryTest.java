package com.dnbn.back.member.repository;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.Role;
import com.dnbn.back.member.model.MemberCreateDto;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;

	@Test
	public void createMemberTest() throws Exception {
	    //given
		MemberCreateDto memberCreateDto = MemberCreateDto.builder()
			.userId("junyeobk")
			.userPw("1234")
			.nickname("jubi")
			.role(Role.ROLE_USER)
			.build();

		Member member = memberCreateDto.toEntity();

	    //when
		memberRepository.save(member);
	    flushAndClear();
		Member findMember = memberRepository.findById(member.getId()).orElseThrow(NoSuchElementException::new);

	    //then
		System.out.println("findMember = " + findMember);
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}

}