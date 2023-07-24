package com.dnbn.back.comment.model;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class CommentDetailDto {
	private Long commentId;
	private Long boardId;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@QueryProjection
	public CommentDetailDto(Long commentId, Long boardId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.commentId = commentId;
		this.boardId = boardId;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
}
