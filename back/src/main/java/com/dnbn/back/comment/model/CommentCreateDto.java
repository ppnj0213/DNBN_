package com.dnbn.back.comment.model;

import com.dnbn.back.comment.entity.Comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CommentCreateDto {
	private String content;
	private Long boardId;
	private Long memberId;

	public Comment toEntity() {
		return Comment.builder()
			.content(content)
			.build();
	}
}
