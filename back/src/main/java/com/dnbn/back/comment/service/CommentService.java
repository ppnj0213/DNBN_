package com.dnbn.back.comment.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.service.BoardService;
import com.dnbn.back.comment.entity.Comment;
import com.dnbn.back.comment.model.CommentCreateDto;
import com.dnbn.back.comment.model.CommentDetailDto;
import com.dnbn.back.comment.model.CommentDtoAssembler;
import com.dnbn.back.comment.repository.CommentRepository;
import com.dnbn.back.common.error.exception.CommentException;
import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardService boardService;
	private final MemberService memberService;

	/**
	 * 댓글 등록
	 */
	@Transactional
	public void createComment(CommentCreateDto commentCreateDto) {
		Board board = boardService.getBoard(commentCreateDto.getBoardId());
		Member member = memberService.getMember(commentCreateDto.getMemberId());
		Comment comment = CommentDtoAssembler.toCommentFromCreateDto(commentCreateDto);
		comment.setBoard(board);
		comment.setMember(member);
		commentRepository.save(comment);
	}

	/**
	 * 댓글 더보기
	 */
	public Slice<CommentDetailDto> getCommentListWithSlice(Pageable pageable, Long boardId) {
		Board findBoard = boardService.getBoard(boardId);
		return commentRepository.findCommentListWithSlice(pageable, findBoard.getId());
	}

	/**
	 * 댓글 삭제
	 */
	@Transactional
	public void deleteComment(Long commentId, Long memberId) {
		Comment comment = getComment(commentId);
		if (comment.matchMember(memberId)) {
			commentRepository.deleteById(commentId);
		} else {
			throw new CommentException(ErrorCode.NO_ACCESS_COMMENT);
		}
	}

	/**
	 * 댓글 조회
	 */
	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
	}
}
