package com.dnbn.back.board.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardDto;
import com.dnbn.back.board.model.BoardSearchCond;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Commit
class BoardRepositoryTest {

	@PersistenceContext
	EntityManager em;
	@Autowired
	BoardRepository boardRepository;

	@DisplayName("querydsl 정상작동 테스트")
	@Test
	public void findBoardTest() throws Exception {
		//when
		List<Board> boardList = boardRepository.getBoardList();

	    //then
		Assertions.assertThat(boardList.size()).isEqualTo(30);
	}

	@DisplayName("무한스크롤 테스트")
	@Test
	public void getBoardListWithSliceTest() throws Exception {
	    //given
		BoardSearchCond cond = new BoardSearchCond("10", "1010", "101010", "junyeobk");

		PageRequest firstRequest = PageRequest.of(0, 5);
		Slice<BoardDto> firstResult = boardRepository.getBoardListWithSlice(cond, firstRequest);
		for (BoardDto boardDto : firstResult) {
			System.out.println("content = " + boardDto.getContent());
		}

		//when
		PageRequest nextRequest = PageRequest.of(firstResult.nextPageable().getPageNumber(), 5);
		Slice<BoardDto> nextResult = boardRepository.getBoardListWithSlice(cond, nextRequest);
		boolean hasNext = firstResult.hasNext();
		while (hasNext) {
			nextRequest = PageRequest.of(nextResult.nextPageable().getPageNumber(), 5);
			nextResult = boardRepository.getBoardListWithSlice(cond, nextRequest);
			for (BoardDto boardDto : nextResult) {
				System.out.println("content = " + boardDto.getContent());
			}
			hasNext = nextResult.hasNext();
		}

		//then
	}

	@BeforeEach
	public void getBoards() {
		List<Board> boards = new ArrayList<>();
		for (int i = 1; i <= 30; i++) {
			Board board = Board.builder()
				.content("content" + i)
				.sido_code("10")
				.sigoon_code("1010")
				.dong_code("101010")
				.openYn("Y")
				.writer("junyeobk")
				.build();
			boards.add(board);
		}
		boardRepository.saveAll(boards);
	}
}