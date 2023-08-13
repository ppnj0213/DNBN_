package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

import com.dnbn.back.member.entity.Region;

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
	private List<Region> regions = new ArrayList<>();

}
