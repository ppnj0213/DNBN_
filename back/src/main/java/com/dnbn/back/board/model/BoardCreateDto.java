package com.dnbn.back.board.model;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import com.dnbn.back.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BoardCreateDto {

	private Long memberId;
	@NotEmpty
	private String sido_code;
	@NotEmpty
	private String sigoon_code;
	@NotEmpty
	private String dong_code;
	@NotEmpty
	private String content;
	@NotEmpty
	private String writer;
	@NotEmpty @Size(max = 1)
	private String openYn;

	public Board toEntity() {
		return Board.builder()
			.sido_code(sido_code)
			.sigoon_code(sigoon_code)
			.dong_code(dong_code)
			.content(content)
			.writer(writer)
			.openYn(openYn.toUpperCase())
			.build();
	}
}
