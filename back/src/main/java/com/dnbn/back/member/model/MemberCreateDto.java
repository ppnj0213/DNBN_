package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.entity.MyRegion;
import com.dnbn.back.member.entity.Role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberCreateDto {

	@NotEmpty
	private String userId;
	@NotEmpty
	private String userPw;
	@NotEmpty
	private String nickname;
	private Role role;
	@NotEmpty @Valid
	private List<MyRegionDto> myRegions = new ArrayList<>();

	public Member toEntity() {
		return Member.builder()
			.userId(userId)
			.userPw(userPw)
			.nickname(nickname)
			.role(role)
			.build();
	}
}
