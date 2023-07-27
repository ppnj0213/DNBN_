package com.dnbn.back.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSearchCond {
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String writer;
	private String openYn;

	@Builder
	public BoardSearchCond(String sido_code, String sigoon_code, String dong_code, String writer, String openYn) {
		this.sido_code = sido_code;
		this.sigoon_code = sigoon_code;
		this.dong_code = dong_code;
		this.writer = writer;
		this.openYn = openYn;
	}
}
