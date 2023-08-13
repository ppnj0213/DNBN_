package com.dnbn.back.comment.model;

import com.dnbn.back.comment.entity.Comment;

public class CommentDtoAssembler {

	public static Comment toCommentFromCreateDto(CommentCreateDto commentCreateDto) {
		return Comment.builder()
			.content(commentCreateDto.getContent())
			.build();
	}
}
