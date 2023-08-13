package com.dnbn.back.member.entity;

import static org.springframework.util.StringUtils.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "REGION")
public class Region {

	@Id @GeneratedValue
	@Column(name = "region_id")
	private Long id;
	private String sido_code;
	private String sido_name;
	private String sigoon_code;
	private String sigoon_name;
	private String dong_code;
	private String dong_name;
	@Column(length = 1)
	private String mainRegionYn;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public void toMainRegion() {
		this.mainRegionYn = "Y";
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void editMyRegion(Region region) {
		if (hasText(region.mainRegionYn)) {
			mainRegionYn = region.mainRegionYn.toUpperCase();
		}
		if (hasText(region.sido_code)) {
			sido_code = region.sido_code;
		}
		if (hasText(region.sido_name))  {
			sido_name = region.sido_name;
		}
		if (hasText(region.sigoon_code)) {
			sigoon_code = region.sigoon_code;
		}
		if (hasText(region.sigoon_name))  {
			sigoon_name = region.sigoon_name;
		}
		if (hasText(region.dong_code)) {
			dong_code = region.dong_code;
		}
		if (hasText(region.dong_name))  {
			dong_name = region.dong_name;
		}
	}
}
