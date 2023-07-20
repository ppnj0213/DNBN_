package com.dnbn.back.board.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;
import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.board.repository.BoardRepository;
import com.dnbn.back.common.exception.BoardException;
import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberService memberService;

	/**
	 * infinite scroll 게시판 조회
	 */
	public Slice<BoardDetailDto> getBoardListWithSlice(Pageable pageable, BoardSearchCond cond) {
		return boardRepository.getBoardListWithSlice(pageable, cond);
	}

	/**
	 * 게시글 저장
	 */
	@Transactional
	public Long createBoard(BoardCreateDto boardCreateDto) {
		Member member = memberService.getMember(boardCreateDto.getMemberId() == null ? 0 : boardCreateDto.getMemberId());
		Board board = boardCreateDto.toEntity();
		board.setMember(member);
		validateMember(board);
		return boardRepository.save(board).getId();
	}

	/**
	 * 게시글 유효성 검사
	 */
	private void validateMember(Board board) {
		board.validateRequiredFields();
	}

	@Transactional
	public Long updateBoard(BoardUpdateDto boardUpdateDto) {
		return null;
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public void deleteBoard(Long boardId) {
		if (boardRepository.existsById(boardId)) {
			boardRepository.deleteById(boardId);
		} else {
			throw new BoardException(ErrorCode.ALREADY_DELETED);
		}
		boardRepository.deleteById(boardId);
	}

	/**
	 * 영속화 용 조회 메서드
	 */
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId)
			.orElseThrow(()->new BoardException(ErrorCode.BOARD_NOT_FOUND));
	}
}
