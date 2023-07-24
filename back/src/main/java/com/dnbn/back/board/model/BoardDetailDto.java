package com.dnbn.back.board.model;

import java.time.LocalDateTime;
import java.util.List;

import com.dnbn.back.comment.model.CommentDetailDto;

import lombok.Data;

@Data
public class BoardDetailDto {
	private Long boardId;
	private Long memberId;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String content;
	private String writer;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private Long likedCnt;
	private List<CommentDetailDto> comments;

	// @QueryProjection
	public BoardDetailDto(Long boardId, Long memberId, String sido_code, String sigoon_code, String dong_code, String content, String writer,
		LocalDateTime createdDate, LocalDateTime modifiedDate, Long likedCnt) {
		this.boardId = boardId;
		this.memberId = memberId;
		this.sido_code = sido_code;
		this.sigoon_code = sigoon_code;
		this.dong_code = dong_code;
		this.content = content;
		this.writer = writer;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.likedCnt = likedCnt;
	}

}
