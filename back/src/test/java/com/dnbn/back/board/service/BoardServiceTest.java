package com.dnbn.back.board.service;

import static com.dnbn.back.board.BoardConstantForTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.BoardConstantForTest;
import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.repository.BoardRepository;

import jakarta.persistence.PersistenceContext;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Mock
	BoardRepository boardRepository;
	@InjectMocks
	BoardService boardService;

	@DisplayName("Mock 객체 생성 테스트")
	@Test
	public void mockito_test() throws Exception {
		assertThat(boardService).isNotNull();
	}

	@DisplayName("게시글 등록")
	@Test
	public void testCreateBoard() throws Exception {
	    //given
		Board board = getBoard();
	    
	    //when
	    
	    //then
	}

	private Board getBoard() {
		return Board.builder()
			.id(1L)
			.content(CONTENTS)
			.writer(WRITER).build();
	}

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