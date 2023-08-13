package com.dnbn.back.comment.model;

import com.dnbn.back.comment.entity.Comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	@NotEmpty
	private String content;
	@NotNull
	private Long boardId;
	@NotNull
	private Long memberId;

}
