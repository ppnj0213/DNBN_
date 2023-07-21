package com.dnbn.back.member.service;

import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.common.exception.MemberException;
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
		// 비밀번호 암호화
		String rawPassword = memberCreateDto.getUserPw();
		String encPassword = !rawPassword.isEmpty() ? bCryptPasswordEncoder.encode(rawPassword) : null; // 공백도 암호화 되는 문제 때문에 validation 체크를 위해 null 처리
		memberCreateDto.setUserPw(encPassword);

		// Member 생성
		Member member = memberCreateDto.toEntity();
		validateMember(member);
		Member savedMember = memberRepository.save(member);

		// MyRegion 생성
		List<MyRegionDto> myRegionDtos = memberCreateDto.getMyRegions();
		for (MyRegionDto myRegionDto : myRegionDtos) {
			MyRegion myRegion = myRegionDto.toEntity();
			validateMyRegion(myRegion);
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

	/**
	 * 내동네 유효성 검사
	 */
	private void validateMyRegion(MyRegion myRegion) {
		//필수값 체크
		myRegion.validateRequiredFields();
	}

	/**
	 *  멤버 유효성 검사
	 */
	private void validateMember(Member member) {
		// 필수값 체크
		member.validateRequiredFields();
		// 아이디, 닉네임 중복체크
		isUserIdDuplicated(member.getUserId());
		isNicknameDuplicated(member.getNickname());
	}

	/**
	 * 아이디 중복체크
	 */
	public boolean isUserIdDuplicated(String userId) {
		if (memberRepository.existsByUserId(userId)) {
			throw new MemberException(ErrorCode.ID_DUPLICATED);
		}
		return true;
	}

	/**
	 * 닉네임 중복체크
	 */
	public boolean isNicknameDuplicated(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new MemberException(ErrorCode.NICKNAME_DUPLICATED);
		}
		return true;
	}

	/**
	 * 영속화 용 멤버 조회 메서드
	 */
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
