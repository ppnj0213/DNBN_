package com.dnbn.back.board.model;

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
public class BoardUpdateDto {
	@NotNull
	private Long memberId;
	@NotEmpty
	private String content;
}
