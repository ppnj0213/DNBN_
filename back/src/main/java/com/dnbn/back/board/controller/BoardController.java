package com.dnbn.back.board.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;
import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.board.model.LikeDto;
import com.dnbn.back.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

	private final BoardService boardService;

	@GetMapping
	public ResponseEntity<Slice<BoardDetailDto>> getBoardListWithSlice(Pageable pageable, BoardSearchCond cond) {
		return ResponseEntity.ok(boardService.getBoardListWithSlice(pageable,cond));
	}

	@PostMapping("/save")
	public ResponseEntity<String> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
		boardService.createBoard(boardCreateDto);
		return ResponseEntity.ok("게시글 등록 success");
	}

	@PutMapping("/{boardId}/save")
	public ResponseEntity<String> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateDto boardUpdateDto) {
		boardService.updateBoard(boardId, boardUpdateDto);
		return ResponseEntity.ok("게시글 수정 success");
	}

	@DeleteMapping("/{boardId}/delete")
	public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
		boardService.deleteBoard(boardId);
		return ResponseEntity.ok("게시글 삭제 success");
	}

	@PostMapping("/{boardId}/like")
	public ResponseEntity<LikeDto> likeOrUnlikeBoard(@PathVariable Long boardId, @RequestParam Long memberId) {
		return ResponseEntity.ok(boardService.likeOrDislike(boardId, memberId));
	}
}
