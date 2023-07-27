package com.dnbn.back.board.repository;

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
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
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
		BoardSearchCond cond = new BoardSearchCond("10", "1010", "101010", "junyeobk", "Y");

		PageRequest firstRequest = PageRequest.of(0, 5);
		Slice<BoardDetailDto> firstResult = boardRepository.getBoardListWithSlice(firstRequest, cond);
		for (BoardDetailDto boardDetailDto : firstResult) {
			System.out.println("content = " + boardDetailDto.getContent());
		}

		//when
		PageRequest nextRequest = PageRequest.of(firstResult.nextPageable().getPageNumber(), 5);
		Slice<BoardDetailDto> nextResult = boardRepository.getBoardListWithSlice(nextRequest, cond);
		boolean hasNext = firstResult.hasNext();
		while (hasNext) {
			nextRequest = PageRequest.of(nextResult.nextPageable().getPageNumber(), 5);
			nextResult = boardRepository.getBoardListWithSlice(nextRequest, cond);
			for (BoardDetailDto boardDetailDto : nextResult) {
				System.out.println("content = " + boardDetailDto.getContent());
			}
			hasNext = nextResult.hasNext();
		}
		//then
	}

	@BeforeEach
	public void createBoards() {
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