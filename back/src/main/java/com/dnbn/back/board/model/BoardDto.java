package com.dnbn.back.board.model;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BoardDto {
	private Long id;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String content;
	private String writer;
	private LocalDateTime createdDate;

	@QueryProjection
	public BoardDto(Long id, String sido_code, String sigoon_code, String dong_code, String content, String writer,
		LocalDateTime createdDate) {
		this.id = id;
		this.sido_code = sido_code;
		this.sigoon_code = sigoon_code;
		this.dong_code = dong_code;
		this.content = content;
		this.writer = writer;
		this.createdDate = createdDate;
	}
}
