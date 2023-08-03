package com.dnbn.back.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.repository.BoardRepository;

import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class BoardServiceTest {

	@Autowired BoardService boardService;
	@Autowired BoardRepository boardRepository;

	@DisplayName("게시글 삭제 테스트")
	@Test
	public void test_delete_board() throws Exception {
	    //given
		boardService.deleteBoard(1L, 1L);

		//when
		List<Board> findBoards = boardRepository.findAll();

		//then
		assertThat(findBoards.size()).isEqualTo(18);
	}

}