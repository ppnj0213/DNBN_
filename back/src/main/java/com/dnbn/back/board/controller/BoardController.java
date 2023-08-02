package com.dnbn.back.board.controller;

import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchDto;
import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.board.model.LikeDto;
import com.dnbn.back.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

	private final BoardService boardService;

	@Description("게시판 조회(무한 스크롤)")
	@PostMapping
	public ResponseEntity<Slice<BoardDetailDto>> getBoardListWithSlice(@RequestBody BoardSearchDto boardSearchDto) {
		return ResponseEntity.ok(boardService.getBoardListWithSlice(boardSearchDto));
	}

	@Description("게시글 신규 저장")
	@PostMapping("/save")
	public ResponseEntity<String> createBoard(@RequestBody @Valid BoardCreateDto boardCreateDto) {
		boardService.createBoard(boardCreateDto);
		return ResponseEntity.ok("게시글 등록 success");
	}

	@Description("게시글 수정")
	@PatchMapping("/{boardId}")
	public ResponseEntity<String> updateBoard(@PathVariable Long boardId, @RequestBody @Valid BoardUpdateDto boardUpdateDto) {
		boardService.updateBoard(boardId, boardUpdateDto);
		return ResponseEntity.ok("게시글 수정 success");
	}

	@Description("게시글 삭제")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<String> deleteBoard(@PathVariable Long boardId, @RequestParam Long memberId) {
		boardService.deleteBoard(boardId, memberId);
		return ResponseEntity.ok("게시글 삭제 success");
	}

	@Description("좋아요/취소")
	@PostMapping("/{boardId}/like")
	public ResponseEntity<LikeDto> likeOrUnlikeBoard(@PathVariable Long boardId, @RequestParam Long memberId) {
		return ResponseEntity.ok(boardService.likeOrDislike(boardId, memberId));
	}
}
