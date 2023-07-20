package com.dnbn.back.member.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.entity.Role;
import com.dnbn.back.member.model.MemberCreateDto;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	@Autowired MyRegionRepository myRegionRepository;
	@Autowired EntityManager em;

	@BeforeEach
	public void createMemberAndMyRegion(){
		List<MyRegion> myRegions = new ArrayList<>();
		MyRegion region1 = MyRegion.builder()
			.isMainRegion("Y")
			.sido_code("10")
			.sido_name("서울시")
			.sigoon_code("1010")
			.sigoon_name("관악구")
			.dong_code("101010")
			.dong_name("신림동")
			.build();

		MyRegion region2 = MyRegion.builder()
			.isMainRegion("Y")
			.sido_code("20")
			.sido_name("부천시")
			.sigoon_code("2020")
			.sigoon_name("원미구")
			.dong_code("202020")
			.dong_name("상동")
			.build();

		myRegions.add(region1);
		myRegions.add(region2);

		Member member = Member.builder()
			.userId("junyeobk")
			.userPw("1234")
			.nickname("주비")
			.role(Role.ROLE_USER)
			.myRegions(myRegions)
			.build();

		memberRepository.save(member);
		myRegionRepository.saveAll(myRegions);
	}

	@DisplayName("유저정보 조회")
	@Test
	public void findUserTest() throws Exception {
	    //given
		Member member = memberRepository.findByUserId("junyeobk").get();
		List<MyRegion> myRegions = member.getMyRegions();

		//then
		assertThat(member.getUserId()).isEqualTo("junyeobk");
		assertThat(myRegions.size()).isEqualTo(2);
		assertThat(myRegions.get(0).getDong_name()).isEqualTo("신림동");
	}

	@DisplayName("아이디 중복체크")
	@Test
	public void idCheck() throws Exception {
		boolean isDuplicate = memberRepository.existsByUserId("junyeobk");
		assertThat(isDuplicate).isTrue();
	}

	@DisplayName("닉네임 중복체크")
	@Test
	public void nicknameCheck() throws Exception {
		boolean isDuplicate = memberRepository.existsByNickname("주비");
		assertThat(isDuplicate).isTrue();
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}

}