package com.dnbn.back.member.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RegionDto {

	private Long id;
	@NotEmpty
	private String sido_code;
	@NotEmpty
	private String sido_name;
	@NotEmpty
	private String sigoon_code;
	@NotEmpty
	private String sigoon_name;
	@NotEmpty
	private String dong_code;
	@NotEmpty
	private String dong_name;
	@NotEmpty @Size(max = 1)
	private String mainRegionYn;

}
