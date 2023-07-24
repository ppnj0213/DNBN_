package com.dnbn.back.comment.service;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.service.BoardService;
import com.dnbn.back.comment.entity.Comment;
import com.dnbn.back.comment.model.CommentCreateDto;
import com.dnbn.back.comment.repository.CommentRepository;
import com.dnbn.back.common.exception.CommentException;
import com.dnbn.back.common.exception.ErrorCode;
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
		Comment comment = commentCreateDto.toEntity();
		comment.setBoard(board);
		comment.setMember(member);
		commentRepository.save(comment);
	}

	/**
	 * 댓글 삭제
	 */
	@Transactional
	public void deleteComment(Long commentId) {
		Comment comment = getComment(commentId);
		if (comment.matchMember(comment.getMember().getId())) {
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
