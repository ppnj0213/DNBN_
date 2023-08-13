package com.dnbn.back.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardCreateDto;
import com.dnbn.back.board.repository.BoardRepository;
import com.dnbn.back.board.service.BoardService;
import com.dnbn.back.comment.model.CommentCreateDto;
import com.dnbn.back.comment.service.CommentService;
import com.dnbn.back.member.entity.Role;
import com.dnbn.back.member.model.MemberCreateDto;
import com.dnbn.back.member.model.RegionDto;
import com.dnbn.back.member.service.MemberService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Profile({"local","test"})
@RequiredArgsConstructor
@Component
public class TestData {

	private final InitMemberService initMemberService;
	private final InitBoardService initBoardService;
	private final InitCommentService initCommentService;

	@PostConstruct
	public void init() {
		initMemberService.init();
		initBoardService.init();
		initCommentService.init();
	}

	@Description("회원 테스트 데이터 생성")
	@Component
	static class InitMemberService {
		@Autowired
		MemberService memberService;

		@Transactional
		public void init() {
			List<RegionDto> regions = new ArrayList<>();
			RegionDto region1 = RegionDto.builder()
				.id(1L)
				.mainRegionYn("Y")
				.sido_code("10")
				.sido_name("서울시")
				.sigoon_code("1010")
				.sigoon_name("강서구")
				.dong_code("101010")
				.dong_name("화곡동")
				.build();

			RegionDto region2 = RegionDto.builder()
				.id(2L)
				.mainRegionYn("N")
				.sido_code("20")
				.sido_name("부천시")
				.sigoon_code("2020")
				.sigoon_name("원미구")
				.dong_code("202020")
				.dong_name("상동")
				.build();

			regions.add(region1);
			regions.add(region2);

			MemberCreateDto memberCreateDto = MemberCreateDto.builder()
				.userId("junyeobk")
				.userPw("1234")
				.nickname("주비")
				.role(Role.ROLE_USER)
				.regions(regions)
				.build();
			memberService.join(memberCreateDto);
		}
	}

	@Description("게시글 테스트 데이터 생성")
	@Component
	static class InitBoardService {
		@Autowired
		BoardService boardService;
		@Autowired
		MemberService memberService;

		@Transactional
		public void init() {
			for (int i = 1; i <= 19; i++) {
				BoardCreateDto boardCreateDto = BoardCreateDto.builder()
					.memberId(1L)
					.content("content" + i)
					.sido_code(i < 11 ? "10" : "20")
					.sigoon_code(i < 11 ? "1010" : "2020")
					.dong_code(i < 11 ? "101010" : "202020")
					.openYn(i < 11 ? "Y" : "N")
					.build();
				boardService.createBoard(boardCreateDto);
			}
		}
	}

	@Description("댓글 테스트 데이터 생성")
	@Component
	static class InitCommentService {
		@Autowired
		CommentService commentService;
		@Autowired
		BoardRepository boardRepository;

		@Transactional
		public void init() {
			List<Board> boards = boardRepository.findAll();
			for (int i=1; i<=boards.size(); i++) {
				CommentCreateDto commentCreateDto = CommentCreateDto.builder()
					.content("comment" + i)
					.boardId(i + 0L)
					.memberId(1L)
					.build();
				commentService.createComment(commentCreateDto);
			}
		}

	}
}
