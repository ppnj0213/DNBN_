package com.dnbn.back.board.model;

import com.dnbn.back.board.entity.BoardType;

import lombok.Data;

@Data
public class BoardSearchDto {
	private BoardType type;
	private Integer page;
	private Integer size;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String writer;
	private String openYn;

	public BoardSearchCond toBoardSearchCond() {
		return BoardSearchCond.builder()
			.sido_code(this.sido_code)
			.sigoon_code(this.sigoon_code)
			.dong_code(this.dong_code)
			.writer(this.writer)
			.openYn(this.openYn)
			.build();
	}
}
