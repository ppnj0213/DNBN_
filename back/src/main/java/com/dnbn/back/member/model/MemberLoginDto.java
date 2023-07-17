package com.dnbn.back.member.model;

import com.dnbn.back.member.entity.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberLoginDto {
	private String userId;
	private String userPw;

	public Member toEntity() {
		return Member.builder()
			.userId(userId)
			.userPw(userPw)
			.build();
	}
}
