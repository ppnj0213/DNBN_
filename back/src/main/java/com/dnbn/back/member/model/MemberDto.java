package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.entity.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberDto {
	private Long memberId;
	private String userId;
	private String userPw;
	private String nickname;
	private Role role;
	private List<MyRegion> myRegions = new ArrayList<>();

	public static MemberDto toMemberDto(Member member) {
		return MemberDto.builder()
			.memberId(member.getId())
			.userId(member.getUserId())
			.userPw(member.getUserPw())
			.nickname(member.getNickname())
			.role(member.getRole())
			.myRegions(member.getMyRegions())
			.build();
	}
}
