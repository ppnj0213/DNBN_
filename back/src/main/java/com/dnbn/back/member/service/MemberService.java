package com.dnbn.back.member.service;

import static org.springframework.util.StringUtils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.common.error.exception.BoardException;
import com.dnbn.back.common.error.exception.MemberException;
import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.MemberDetailDto;
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
	 */
	@Transactional
	public Long join(MemberCreateDto memberCreateDto) {
		/***************  Member ***************/
		// 아이디 중복 체크
		if (isUserIdDuplicated(memberCreateDto.getUserId())) {
			throw new BoardException(ErrorCode.ID_DUPLICATED);
		}
		// 닉네임 중복 체크
		if (isNicknameDuplicated(memberCreateDto.getNickname())) {
			throw new BoardException(ErrorCode.NICKNAME_DUPLICATED);
		}
		// 비밀번호 암호화
		String rawPassword = memberCreateDto.getUserPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		memberCreateDto.setUserPw(encPassword);
		// 저장
		Member member = memberCreateDto.toEntity();
		Member savedMember = memberRepository.save(member);

		/***************  MyRegion ***************/
		List<MyRegionDto> myRegionDtos = memberCreateDto.getMyRegions();
		checkMainRegion(myRegionDtos);
		for (MyRegionDto myRegionDto : myRegionDtos) {
			// 저장
			MyRegion myRegion = myRegionDto.toEntity();
			myRegion.setMember(savedMember);
			myRegionRepository.save(myRegion);
		}
		return savedMember.getId();
	}

	/**
	 * 회원정보 조회 (마이페이지)
	 */
	public MemberDetailDto getMemberDetail(Long memberId) {
		Member member = getMember(memberId);
		return MemberDetailDto.toMemberDto(member);
	}

	/**
	 * 회원정보 수정 (마이페이지)
	 */
	@Transactional
	public Long updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
		// Member, MyRegion fetch join
		Member member = memberRepository.findByIdWithMyRegion(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
		/***************  Member ***************/
		// 비밀번호 변경된 경우 암호화
		if (hasText(memberUpdateDto.getUserPw())) {
			String rawPassword = memberUpdateDto.getUserPw();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			memberUpdateDto.setUserPw(encPassword);
		}
		// Member 수정
		member.editMember(memberUpdateDto);

		/***************  MyRegion ***************/
		checkMainRegion(memberUpdateDto.getMyRegions());
		// MyRegion 변경된 경우 PK 값으로 비교 후 update
		for (MyRegion myRegion : member.getMyRegions()) {
			for (MyRegionDto myRegionDto : memberUpdateDto.getMyRegions()) {
				if (myRegion.getId().equals(myRegionDto.getId())) {
					myRegion.editMyRegion(myRegionDto.toEntity());
				}
			}
		}
		return member.getId();
	}

	/**
	 * 메인 지역 미설정, 또는 중복 설정된 경우 예외 발생
	 */
	private void checkMainRegion(List<MyRegionDto> myRegionDtos) {
		Set<String> regionSet = new HashSet<>();
		for (MyRegionDto myRegionDto : myRegionDtos) {
			regionSet.add(myRegionDto.getMainRegionYn());
		}
		// 대표 동네가 두 개, 또는 하나도 없는 경우 예외 발생
		if (regionSet.size() == 1) {
			throw new MemberException(ErrorCode.MAIN_REGION_UNACCEPTABLE);
		}
	}

	/**
	 * 아이디 중복체크
	 */
	public boolean isUserIdDuplicated(String userId) {
		return memberRepository.existsByUserId(userId);
	}

	/**
	 * 닉네임 중복체크
	 */
	public boolean isNicknameDuplicated(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	/**
	 * 회원 조회
	 */
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
