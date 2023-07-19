package com.dnbn.back.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardDto;
import com.dnbn.back.board.model.BoardSearchCond;

public interface BoardRepositoryCustom {
	List<Board> getBoardList();
	Slice<BoardDto> getBoardListWithSlice(BoardSearchCond cond, Pageable pageable);
}
