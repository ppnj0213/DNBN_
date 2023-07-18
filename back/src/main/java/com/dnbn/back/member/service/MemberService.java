package com.dnbn.back.member.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.entity.Role;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.repository.MemberRepository;
import com.dnbn.back.member.repository.MyRegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final MyRegionRepository myRegionRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 회원가입
	 * memberCreateDto 에서 password를 암호화한 뒤 저장한다.
	 */
	@Transactional
	public void join(MemberCreateDto memberCreateDto) {
		String rawPassword = memberCreateDto.getUserPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		memberCreateDto.setUserPw(encPassword);
		memberCreateDto.setRole(Role.ROLE_USER);

		// Member 생성
		Member member = memberCreateDto.toEntity();
		Member savedMember = memberRepository.save(member);

		// MyRegion 생성
		List<MyRegion> myRegions = memberCreateDto.getMyRegions();
		for (MyRegion myRegion : myRegions) {
			myRegion.setMember(savedMember);
			myRegionRepository.save(myRegion);
		}
	}

	/**
	 * 아이디 중복체크
	 */
	public boolean checkUserId(String userId) {
		return memberRepository.existsByUserId(userId);
	}

	/**
	 * 닉네임 중복체크
	 */
	public boolean checkNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	/**
	 * 회원정보 조회
	 */
	public MemberDto getMemberDetail(Long memberId) {
		Member member = getMember(memberId);
		return MemberDto.toMemberDto(member);
	}

	/**
	 * 회원정보 수정
	 */
	@Transactional
	public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
		List<Member> result = memberRepository.findByIdWithMyRegion(memberId);

		String rawPassword = memberUpdateDto.getUserPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		memberUpdateDto.setUserPw(encPassword);

		// 기본정보 update
		// member.editMember(memberUpdateDto);
		// 동네정보 update
		for (MyRegion myRegion : memberUpdateDto.getMyRegions()) {
			myRegion.editMyRegion(myRegion);
		}
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
	}
}
