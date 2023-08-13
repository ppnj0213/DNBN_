package com.dnbn.back.board.service;

import static com.dnbn.back.board.BoardConstantForTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.BoardConstantForTest;
import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.model.BoardDtoAssembler;
import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.board.repository.BoardRepository;
import com.dnbn.back.member.entity.Member;
import com.dnbn.back.member.repository.MemberRepository;
import com.dnbn.back.member.service.MemberService;

import jakarta.persistence.PersistenceContext;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Mock
	BoardRepository boardRepository;
	@Mock
	MemberService memberService;
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
		given(boardRepository.save(any(Board.class)))
			.willReturn(board); // -> 어떤 Board(any(Board.class))를 저장하면 내가 생성한 Board(getBoard)를 리턴함. -> boardId가 생성됨
		BoardCreateDto boardCreateDto = BoardDtoAssembler.toBoardCreateDto(board);
	    
	    //when
		Long boardId = boardService.createBoard(boardCreateDto);

		//then
		assertThat(boardId).isNotNull();
		verify(boardRepository,times(1))
			.save(any(Board.class));
		// then(boardRepository).should(times(1)).save(any(Board.class));
	}

	@DisplayName("게시글 수정")
	@Test
	public void testUpdateBoard() throws Exception {
	    //given
		Board board = getBoard();
		BoardUpdateDto boardUpdateDto = getBoardUpdateDto("update contents");

		given(boardRepository.findById(anyLong()))
			.willReturn(Optional.of(board));

		//when
		Long result = boardService.updateBoard(1L, boardUpdateDto);

		//then
		assertThat(1L).isEqualTo(result);
		verify(boardRepository, times(1)).findById(anyLong());
	}

	private BoardUpdateDto getBoardUpdateDto(String updateContents) {
		return BoardUpdateDto.builder()
			.memberId(1L)
			.content(updateContents)
			.build();
	}

	private Board getBoard() {
		Member member = getMember();
		when(memberService.getMember(1L)).thenReturn(member); // 가짜 멤버 객체 주입

		return Board.builder()
			.id(BOARD_ID)
			.content(CONTENTS)
			.writer(WRITER)
			.openYn("Y")
			.member(member)
			.build();
	}

	private Member getMember() {
		return Member.builder()
			.id(1L)
			.userId("junyeobk")
			.build();
	}
}