package com.dnbn.back.member.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
			.sido_code("1010")
			.sido_name("관악구")
			.sido_code("101010")
			.sido_name("신림동")
			.build();

		MyRegion region2 = MyRegion.builder()
			.isMainRegion("Y")
			.sido_code("20")
			.sido_name("부천시")
			.sido_code("2020")
			.sido_name("원미구")
			.sido_code("202020")
			.sido_name("상동")
			.build();

		myRegions.add(region1);
		myRegions.add(region2);

		Member member = Member.builder()
			.userId("kim")
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
		List<Member> result = memberRepository.findByIdWithMyRegion(1L);
		//when
		for (Member member : result) {
			System.out.println("member = " + member);
		}

	    //then
	}

	@DisplayName("유저 정보 업데이트")
	@Test
	public void updateTest() throws Exception {
	    //given

	    //when

	    //then
	}


	public void flushAndClear() {
		em.flush();
		em.clear();
	}

}