package com.dnbn.back.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.comment.model.CommentCreateDto;
import com.dnbn.back.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/save")
	public ResponseEntity<String> createComment(CommentCreateDto commentCreateDto) {
		commentService.createComment(commentCreateDto);
		return ResponseEntity.ok("댓글 등록 success");
	}

	@DeleteMapping("/{commentId}/delete")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok("댓글 삭제 success");
	}
}
