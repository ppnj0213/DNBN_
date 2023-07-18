package com.dnbn.back.member.service;

import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.common.exception.MemberException;
import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.entity.Role;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDto;
import com.dnbn.back.member.model.MemberUpdateDto;
import com.dnbn.back.member.model.MyRegionDto;
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
	public Long join(MemberCreateDto memberCreateDto) {
		String rawPassword = memberCreateDto.getUserPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		memberCreateDto.setUserPw(encPassword);
		memberCreateDto.setRole(Role.ROLE_USER);

		// Member 생성
		Member member = memberCreateDto.toEntity();
		member.validateRequiredFields(); // 필수값 체크
		checkUserId(member.getUserId());
		checkNickname(member.getNickname());
		Member savedMember = memberRepository.save(member);

		// MyRegion 생성
		List<MyRegionDto> myRegionDtos = memberCreateDto.getMyRegions();
		for (MyRegionDto myRegionDto : myRegionDtos) {
			MyRegion myRegion = myRegionDto.toEntity();
			myRegion.validateRequiredFields(); //필수값 체크
			myRegion.setMember(savedMember);
			myRegionRepository.save(myRegion);
		}
		return savedMember.getId();
	}

	/**
	 * 아이디 중복체크
	 */
	public boolean checkUserId(String userId) {
		if (memberRepository.existsByUserId(userId)) {
			throw new MemberException(ErrorCode.ID_DUPLICATED);
		}
		return true;
	}

	/**
	 * 닉네임 중복체크
	 */
	public boolean checkNickname(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new MemberException(ErrorCode.NICKNAME_DUPLICATED);
		}
		return true;
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
	public Long updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
		Member member = memberRepository.findByIdWithMyRegion(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
		List<MyRegion> myRegions = member.getMyRegions();

		// 비밀번호 변경된 경우 암호화
		if (hasText(memberUpdateDto.getUserPw())) {
			String rawPassword = memberUpdateDto.getUserPw();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			memberUpdateDto.setUserPw(encPassword);
		}
		member.editMember(memberUpdateDto); // 기본정보 update

		// 동네 변경된 경우 id로 체크
		myRegions.forEach(myRegion -> {
			memberUpdateDto.getMyRegions()
				.stream()
				.filter(myRegionDto -> myRegion.getId().equals(myRegionDto.getId()))
				.forEachOrdered(myRegionDto -> myRegion.editMyRegion(myRegionDto.toEntity())); // 동네정보 update
		});
		return member.getId();
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
