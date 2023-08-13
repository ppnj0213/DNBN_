package com.dnbn.back.member.model;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.Region;

public class MemberDtoAssembler {

	public static Member toMemberFromCreateDto(MemberCreateDto memberCreateDto) {
		return Member.builder()
			.userId(memberCreateDto.getUserId())
			.userPw(memberCreateDto.getUserPw())
			.nickname(memberCreateDto.getNickname())
			.role(memberCreateDto.getRole())
			.build();
	}

	public static Region toMyRegionFromCreateDto(RegionDto regionDto) {
		return Region.builder()
			.id(regionDto.getId())
			.mainRegionYn(regionDto.getMainRegionYn())
			.sido_code(regionDto.getSido_code())
			.sido_name(regionDto.getSido_name())
			.sigoon_code(regionDto.getSigoon_code())
			.sigoon_name(regionDto.getSigoon_name())
			.dong_code(regionDto.getDong_code())
			.dong_name(regionDto.getDong_name())
			.build();
	}

	public static MemberDetailDto toMemberDetailDto(Member member) {
		return MemberDetailDto.builder()
			.memberId(member.getId())
			.userId(member.getUserId())
			.nickname(member.getNickname())
			.regions(member.getRegions())
			.build();
	}
}
