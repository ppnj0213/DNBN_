package com.dnbn.back.comment.controller;

import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.comment.model.CommentCreateDto;
import com.dnbn.back.comment.model.CommentDetailDto;
import com.dnbn.back.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {
	private final CommentService commentService;

	@Description("댓글 더보기")
	@GetMapping
	public ResponseEntity<Slice<CommentDetailDto>> getCommentListWithSlice(Pageable pageable, @RequestParam Long boardId) {
		return ResponseEntity.ok(commentService.getCommentListWithSlice(pageable, boardId));
	}

	@Description("댓글 저장")
	@PostMapping("/save")
	public ResponseEntity<String> createComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {
		commentService.createComment(commentCreateDto);
		return ResponseEntity.ok("댓글 등록 success");
	}

	@Description("댓글 삭제")
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestParam Long memberId) {
		commentService.deleteComment(commentId, memberId);
		return ResponseEntity.ok("댓글 삭제 success");
	}
}
