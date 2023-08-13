package com.dnbn.back.board.model;

import com.dnbn.back.board.entity.Board;

public class BoardDtoAssembler {

	public static BoardCreateDto toBoardCreateDto(Board board) {
		return BoardCreateDto.builder()
			.sido_code(board.getSido_code())
			.sigoon_code(board.getSigoon_code())
			.dong_code(board.getDong_code())
			.content(board.getContent())
			.openYn(board.getOpenYn())
			.memberId(board.getMember().getId())
			.build();
	}

	public static Board toBoardFromCreateDto(BoardCreateDto boardCreateDto) {
		return Board.builder()
			.sido_code(boardCreateDto.getSido_code())
			.sigoon_code(boardCreateDto.getSigoon_code())
			.dong_code(boardCreateDto.getDong_code())
			.content(boardCreateDto.getContent())
			.openYn(boardCreateDto.getOpenYn().toUpperCase())
			.build();
	}
}
