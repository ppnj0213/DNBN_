package com.dnbn.back.member.service;

import static org.springframework.util.StringUtils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.common.error.ErrorCode;
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
		// 1. Member
		// 1.1. 비밀번호 암호화
		String rawPassword = memberCreateDto.getUserPw();
		String encPassword = !rawPassword.isEmpty() ? bCryptPasswordEncoder.encode(rawPassword) : null; // 공백도 암호화 되는 문제 때문에 validation 체크를 위해 null 처리
		memberCreateDto.setUserPw(encPassword);
		// 1.2. Member 엔티티 변환
		Member member = memberCreateDto.toEntity();
		// 1.3. Member 유효성 검사
		member.validateRequiredFields();
		isUserIdDuplicated(member.getUserId());
		isNicknameDuplicated(member.getNickname());
		// 1.4. Member 저장
		Member savedMember = memberRepository.save(member);

		// 2. MyRegion
		List<MyRegionDto> myRegionDtos = memberCreateDto.getMyRegions();
		// 2.1. 대표 동네 중복 체크 용 Set 생성
		Set<String> regionSet = new HashSet<>();
		for (MyRegionDto myRegionDto : myRegionDtos) {
			// 2.2. MyRegion 엔티티 변환
			MyRegion myRegion = myRegionDto.toEntity();
			// 2.3. validation
			myRegion.validateRequiredFields();
			// 2.4. Member 주입
			myRegion.setMember(savedMember);
			// 2.5. MyRegion 저장
			myRegionRepository.save(myRegion);
			regionSet.add(myRegionDto.getMainRegionYn());
		}
		// 2.6. 대표 동네가 두 개, 또는 하나도 없는 경우 예외 발생
		if (regionSet.size() == 1) {
			throw new MemberException(ErrorCode.MAIN_REGION_UNACCEPTABLE);
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
		// 0. Member, MyRegion fetch join
		Member member = memberRepository.findByIdWithMyRegion(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
		// 1. Member
		// 1.1. 비밀번호 변경된 경우 암호화
		if (hasText(memberUpdateDto.getUserPw())) {
			String rawPassword = memberUpdateDto.getUserPw();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			memberUpdateDto.setUserPw(encPassword);
		}
		// 1.2. Member 수정
		member.editMember(memberUpdateDto);

		// 2. MyRegion
		// 2.1. MyRegion 변경된 경우 PK 값으로 비교 후 update
		member.getMyRegions().forEach(myRegion -> {
			memberUpdateDto.getMyRegions()
				.stream()
				.filter(myRegionDto -> myRegion.getId().equals(myRegionDto.getId()))
				.forEachOrdered(myRegionDto -> myRegion.editMyRegion(myRegionDto.toEntity())); // 동네정보 update
		});
		return member.getId();
	}

	/**
	 * 아이디 중복체크
	 */
	public boolean isUserIdDuplicated(String userId) {
		if (memberRepository.existsByUserId(userId)) {
			throw new MemberException(ErrorCode.ID_DUPLICATED);
		}
		return false;
	}

	/**
	 * 닉네임 중복체크
	 */
	public boolean isNicknameDuplicated(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new MemberException(ErrorCode.NICKNAME_DUPLICATED);
		}
		return false;
	}

	/**
	 * 회원 조회
	 */
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
