package com.dnbn.back.member.model;

import java.util.List;

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
public class MyRegionDto {

	private Long id;
	private String sido_code;
	private String sido_name;
	private String sigoon_code;
	private String sigoon_name;
	private String dong_code;
	private String dong_name;
	private String isMainRegion;

	public MyRegion toEntity() {
		return MyRegion.builder()
			.id(id)
			.isMainRegion(isMainRegion.isEmpty() ? "N" : isMainRegion)
			.sido_code(sido_code)
			.sido_name(sido_name)
			.sigoon_code(sigoon_code)
			.sigoon_name(sigoon_name)
			.dong_code(dong_code)
			.dong_name(dong_name)
			.build();
	}

}
