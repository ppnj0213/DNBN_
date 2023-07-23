package com.dnbn.back.board.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.entity.Like;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;
import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.board.model.LikeDto;
import com.dnbn.back.board.repository.BoardRepository;
import com.dnbn.back.board.repository.LikeRepository;
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
	private final LikeRepository likeRepository;

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
		Board board = boardCreateDto.toEntity();
		Member member = memberService.getMember(boardCreateDto.getMemberId() == null ? 0 : boardCreateDto.getMemberId());
		board.setMember(member);
		// validation
		validateBoard(board);
		return boardRepository.save(board).getId();
	}

	/**
	 * 게시글 수정
	 */
	@Transactional
	public Long updateBoard(Long boardId, BoardUpdateDto boardUpdateDto) {
		Long memberId = boardUpdateDto.getMemberId();

		Board board = getBoard(boardId);
		// validation
		if (!board.matchMember(memberId)) {
			throw new BoardException(ErrorCode.MEMBER_NO_MATCH);
		}
		validateBoard(board);
		board.editBoard(boardUpdateDto);

		return board.getId();
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
	}

	/**
	 * 좋아요/취소
	 */
	@Transactional
	public LikeDto likeOrDislike(Long boardId, Long memberId) {
		// boardId와 memberId로 좋아요 테이블 탐색
		Like findLike = likeRepository.findByBoardIdAndMemberId(boardId, memberId).orElse(null);
		LikeDto likeDto = new LikeDto();
		// 좋아요 취소
		if (findLike != null) {
			likeRepository.deleteById(findLike.getId()); // 기존 매핑정보 삭제
			likeDto.setLiked(false);
		// 좋아요
		} else {
			Like newLike = Like.builder()
				.board(getBoard(boardId))
				.member(memberService.getMember(memberId))
				.build();
			likeRepository.save(newLike); // 신규 매핑정보 생성
			likeDto.setLiked(true);
		}
		// 전체 좋아요 개수
		likeDto.setTotalCount(likeRepository.getCountByBoardId(boardId));
		return likeDto;
	}

	/**
	 * 게시글 유효성 검사
	 */
	private void validateBoard(Board board) {
		board.validateRequiredFields();
	}

	/**
	 * 영속화 용 조회 메서드
	 */
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId)
			.orElseThrow(()->new BoardException(ErrorCode.BOARD_NOT_FOUND));
	}
}
