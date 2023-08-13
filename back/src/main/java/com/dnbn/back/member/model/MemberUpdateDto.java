package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
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
public class MemberUpdateDto {
	private String userPw;
	private String nickname;
	@NotEmpty @Valid
	private List<RegionDto> regions = new ArrayList<>();
}
