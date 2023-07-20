package com.dnbn.back.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardSearchCond {
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String writer;
}
