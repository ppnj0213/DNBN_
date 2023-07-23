package com.dnbn.back.board.model;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BoardDetailDto {
	private Long id;
	private Long memberId;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String content;
	private String writer;
	private LocalDateTime createdDate;
	private Long likedCnt;

	@QueryProjection
	public BoardDetailDto(Long id, Long memberId, String sido_code, String sigoon_code, String dong_code, String content, String writer,
		LocalDateTime createdDate, Long likedCnt) {
		this.id = id;
		this.memberId = memberId;
		this.sido_code = sido_code;
		this.sigoon_code = sigoon_code;
		this.dong_code = dong_code;
		this.content = content;
		this.writer = writer;
		this.createdDate = createdDate;
		this.likedCnt = likedCnt;
	}

}
