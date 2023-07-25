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
import com.dnbn.back.common.error.exception.BoardException;
import com.dnbn.back.common.error.ErrorCode;
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
	 * 게시글 등록
	 */
	@Transactional
	public Long createBoard(BoardCreateDto boardCreateDto) {
		// 1. Board 엔티티 변환
		Board board = boardCreateDto.toEntity();
		// 2. 사용자 조회
		Long dtoMemberId = boardCreateDto.getMemberId();
		Member member = memberService.getMember(dtoMemberId == null ? 0 : dtoMemberId);
		// 3. 게시판 엔티티에 사용자 매핑
		board.setMember(member);
		// 4. validation
		validateBoard(board);
		// 5. 저장
		return boardRepository.save(board).getId();
	}

	/**
	 * 게시판 조회
	 */
	public Slice<BoardDetailDto> getBoardListWithSlice(Pageable pageable, BoardSearchCond cond) {
		return boardRepository.getBoardListWithSlice(pageable, cond);
	}

	/**
	 * 게시글 수정
	 */
	@Transactional
	public Long updateBoard(Long boardId, BoardUpdateDto boardUpdateDto) {
		// 1. 게시글 조회
		Board board = getBoard(boardId);
		// 2. validation
		if (!board.matchMember(boardUpdateDto.getMemberId())) {
			throw new BoardException(ErrorCode.NO_ACCESS_BOARD);
		}
		validateBoard(board);
		// 3. 게시글 update
		board.editBoard(boardUpdateDto);
		return board.getId();
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public void deleteBoard(Long boardId) {
		// 1. 게시글 조회
		Board board = getBoard(boardId);
		// 2. 해당 게시글 등록한 사용자인지 체크
		if (board.matchMember(board.getMember().getId())) {
			boardRepository.deleteById(boardId);
		} else {
			throw new BoardException(ErrorCode.NO_ACCESS_BOARD);
		}
	}

	/**
	 * 좋아요/취소
	 */
	@Transactional
	public LikeDto likeOrDislike(Long boardId, Long memberId) {
		// 1. boardId와 memberId로 좋아요 테이블 탐색
		Like findLike = likeRepository.findByBoardIdAndMemberId(boardId, memberId).orElse(null);
		// 2. 응답 객체 생성
		LikeDto likeDto = new LikeDto();
		// 3. 좋아요 테이블에 매핑정보가 있을 경우 좋아요, 아닌경우 좋아요 취소
		if (findLike != null) {
			likeRepository.deleteById(findLike.getId()); // 기존 매핑정보 삭제
			likeDto.setLiked(false);
		} else {
			Like newLike = Like.builder()
				.board(getBoard(boardId))
				.member(memberService.getMember(memberId))
				.build();
			likeRepository.save(newLike); // 신규 매핑정보 생성
			likeDto.setLiked(true);
		}
		// 4. 전체 좋아요 개수 set
		likeDto.setTotalCount(likeRepository.getCountByBoardId(boardId));
		return likeDto;
	}

	/**
	 * 게시글 유효성 검사
	 */
	private void validateBoard(Board board) {
		// 1. 필수값 체크
		board.validateRequiredFields();
	}

	/**
	 * 게시글 조회
	 */
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId)
			.orElseThrow(()->new BoardException(ErrorCode.BOARD_NOT_FOUND));
	}
}
