package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberDetailDto {
	private Long memberId;
	private String userId;
	private String nickname;
	private List<MyRegion> myRegions = new ArrayList<>();

	public static MemberDetailDto toMemberDto(Member member) {
		return MemberDetailDto.builder()
			.memberId(member.getId())
			.userId(member.getUserId())
			.nickname(member.getNickname())
			.myRegions(member.getMyRegions())
			.build();
	}
}
