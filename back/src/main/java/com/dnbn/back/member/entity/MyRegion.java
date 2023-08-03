package com.dnbn.back.member.entity;

import static org.springframework.util.StringUtils.*;

import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.common.error.exception.MemberException;
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
@Table(name = "MY_REGION")
public class MyRegion {

	@Id @GeneratedValue
	@Column(name = "myregion_id")
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

	public void editMyRegion(MyRegion myRegion) {
		if (hasText(myRegion.mainRegionYn)) {
			mainRegionYn = myRegion.mainRegionYn.toUpperCase();
		}
		if (hasText(myRegion.sido_code)) {
			sido_code = myRegion.sido_code;
		}
		if (hasText(myRegion.sido_name))  {
			sido_name = myRegion.sido_name;
		}
		if (hasText(myRegion.sigoon_code)) {
			sigoon_code = myRegion.sigoon_code;
		}
		if (hasText(myRegion.sigoon_name))  {
			sigoon_name = myRegion.sigoon_name;
		}
		if (hasText(myRegion.dong_code)) {
			dong_code = myRegion.dong_code;
		}
		if (hasText(myRegion.dong_name))  {
			dong_name = myRegion.dong_name;
		}
	}
}
