package com.dnbn.back.board.model;

import com.dnbn.back.board.entity.BoardType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class BoardSearchDto {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BoardType type;
	private Integer page;
	private Integer size;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String writer;
	private Long memberId;
	private String openYn;

	public BoardSearchCond toBoardSearchCond() {
		return BoardSearchCond.builder()
			.sido_code(this.sido_code)
			.sigoon_code(this.sigoon_code)
			.dong_code(this.dong_code)
			.writer(this.writer)
			.memberId(this.memberId)
			.openYn(this.openYn)
			.build();
	}
}
