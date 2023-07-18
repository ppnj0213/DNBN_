package com.dnbn.back.member.model;

import java.util.ArrayList;
import java.util.List;

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
public class MemberUpdateDto {
	private String userPw;
	private String nickname;
	private List<MyRegion> myRegions = new ArrayList<>();
}
