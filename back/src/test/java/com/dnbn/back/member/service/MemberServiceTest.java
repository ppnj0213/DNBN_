package com.dnbn.back.member.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDetailDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.model.RegionDto;
import com.dnbn.back.member.repository.MemberRepository;

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
		assertThat(findMember.getRegions().size()).isEqualTo(2);
		assertThat(findMember.getRegions().get(0).getDong_name()).isEqualTo("신림동");
	}
	
	@DisplayName("회원정보 조회 서비스")
	@Test
	public void findMember() throws Exception {
	    //given
		Long memberId = getMemberIdAfterjoin();
		flushAndClear();

		//when
		MemberDetailDto memberDetailDto = memberService.getMemberDetail(memberId);

		//then
		assertThat(memberDetailDto.getNickname()).isEqualTo("주비");
		assertThat(memberDetailDto.getRegions().size()).isEqualTo(2);
		assertThat(memberDetailDto.getRegions().get(0).getDong_name()).isEqualTo("신림동");
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
		assertThat(findMember.getRegions().get(0).getDong_name()).isEqualTo("화곡동");
	}

	private Long getMemberIdAfterjoin() {
		MemberCreateDto memberCreateDto = getMemberCreateDto();
		return memberService.join(memberCreateDto);
	}

	private MemberUpdateDto getMemeberUpdateDto() {
		List<RegionDto> myRegions = new ArrayList<>();
		RegionDto region1 = RegionDto.builder()
			.id(1L)
			.mainRegionYn("Y")
			.sido_code("10")
			.sido_name("서울시")
			.sigoon_code("1010")
			.sigoon_name("강서구")
			.dong_code("101010")
			.dong_name("화곡동")
			.build();

		RegionDto region2 = RegionDto.builder()
			.id(2L)
			.mainRegionYn("N")
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
			.regions(myRegions)
			.build();
	}

	private MemberCreateDto getMemberCreateDto() {
		List<RegionDto> myRegions = new ArrayList<>();
		RegionDto region1 = RegionDto.builder()
			.mainRegionYn("Y")
			.sido_code("10")
			.sido_name("서울시")
			.sigoon_code("1010")
			.sigoon_name("관악구")
			.dong_code("101010")
			.dong_name("신림동")
			.build();

		RegionDto region2 = RegionDto.builder()
			.mainRegionYn("Y")
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
			.regions(myRegions)
			.build();
	}

	public void flushAndClear() {
		em.flush();
		em.clear();
	}
}