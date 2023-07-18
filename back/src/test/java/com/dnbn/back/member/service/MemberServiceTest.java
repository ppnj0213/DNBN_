package com.dnbn.back.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
import com.dnbn.back.member.model.MemberDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.model.MyRegionDto;
import com.dnbn.back.member.repository.MemberRepository;
import com.dnbn.back.member.repository.MyRegionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@PersistenceContext
	EntityManager em;
	@Autowired
	MemberRepository memberRepository;
	
	@DisplayName("회원가입 서비스")
	@Test
	public void joinMemberTest() throws Exception {
	    //given
		MemberCreateDto memberCreateDto = getMemberCreateDto();
		Long memberid = memberService.join(memberCreateDto);
		flushAndClear();

		//when
		Member findMember = memberRepository.findById(memberid).get();

		//then
		assertThat(findMember.getNickname()).isEqualTo("주비");
		assertThat(findMember.getMyRegions().size()).isEqualTo(2);
		assertThat(findMember.getMyRegions().get(0).getDong_name()).isEqualTo("신림동");
	}
	
	@DisplayName("회원정보 조회 서비스")
	@Test
	public void findMember() throws Exception {
	    //given
		Long memberId = getMemberIdAfterjoin();
		flushAndClear();

		//when
		MemberDto memberDto = memberService.getMemberDetail(memberId);

		//then
		assertThat(memberDto.getNickname()).isEqualTo("주비");
		assertThat(memberDto.getMyRegions().size()).isEqualTo(2);
		assertThat(memberDto.getMyRegions().get(0).getDong_name()).isEqualTo("신림동");
	}

	@DisplayName("회원정보 수정 서비스")
	@Test
	public void updateMemberTest() throws Exception {
	    //given
		MemberCreateDto memberCreateDto = getMemberCreateDto();
		Long memberId = memberService.join(memberCreateDto);

		flushAndClear();

		//when
		MemberUpdateDto memberUpdateDto = getMemeberUpdateDto();
		memberService.updateMember(memberId, memberUpdateDto);

		flushAndClear();

		Member findMember = memberRepository.findById(memberId).get();

		//then
		assertThat(findMember.getNickname()).isEqualTo("주비비");
		assertThat(findMember.getMyRegions().get(0).getDong_name()).isEqualTo("화곡동");
	}

	private Long getMemberIdAfterjoin() {
		MemberCreateDto memberCreateDto = getMemberCreateDto();
		return memberService.join(memberCreateDto);
	}

	private MemberUpdateDto getMemeberUpdateDto() {
		List<MyRegionDto> myRegions = new ArrayList<>();
		MyRegionDto region1 = MyRegionDto.builder()
			.id(1L)
			.isMainRegion("Y")
			.sido_code("10")
			.sido_name("서울시")
			.sigoon_code("1010")
			.sigoon_name("강서구")
			.dong_code("101010")
			.dong_name("화곡동")
			.build();

		MyRegionDto region2 = MyRegionDto.builder()
			.id(2L)
			.isMainRegion("N")
			.sido_code("20")
			.sido_name("부천시")
			.sigoon_code("2020")
			.sigoon_name("원미구")
			.dong_code("202020")
			.dong_name("상동")
			.build();

		myRegions.add(region1);
		myRegions.add(region2);

		return MemberUpdateDto.builder()
			.nickname("주비비")
			.userPw("12345")
			.myRegions(myRegions)
			.build();
	}

	private MemberCreateDto getMemberCreateDto() {
		List<MyRegionDto> myRegions = new ArrayList<>();
		MyRegionDto region1 = MyRegionDto.builder()
			.isMainRegion("Y")
			.sido_code("10")
			.sido_name("서울시")
			.sigoon_code("1010")
			.sigoon_name("관악구")
			.dong_code("101010")
			.dong_name("신림동")
			.build();

		MyRegionDto region2 = MyRegionDto.builder()
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

		return MemberCreateDto.builder()
			.userId("junyeobk")
			.userPw("1234")
			.nickname("주비")
			.myRegions(myRegions)
			.build();
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}
}