package com.dnbn.back.board.repository;

import static com.dnbn.back.board.entity.QBoard.*;
import static com.dnbn.back.board.entity.QLike.*;
import static com.dnbn.back.comment.entity.QComment.*;
import static com.querydsl.jpa.JPAExpressions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.entity.BoardType;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;
import com.dnbn.back.comment.model.CommentDetailDto;
import com.dnbn.back.comment.model.QCommentDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public BoardRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<Board> getBoardList() {
		return queryFactory
			.selectFrom(board)
			.fetch();
	}

	@Override
	public Slice<BoardDetailDto> getBoardListWithSlice(BoardType type, Pageable pageable, BoardSearchCond cond) {
		// 게시글 조회
		List<BoardDetailDto> result =
			queryFactory.select(
				Projections.constructor(BoardDetailDto.class,
					board.id,
					board.member.id,
					board.sido_code,
					board.sigoon_code,
					board.dong_code,
					board.content,
					board.writer,
					board.createdDate,
					board.modifiedDate,
					select(like.id.count())
						.from(like)
						.where(like.board.id.eq(board.id)),
					select(new CaseBuilder()
						.when(comment.count().gt(1)).then(true)
						.otherwise(false))
						.from(comment)
						.where(comment.board.id.eq(board.id))
				)
			)
			.from(board)
			.where(sidoCodeEq(cond.getSido_code()),
				   sigoonCodeEq(cond.getSigoon_code()),
				   dongCodeEq(cond.getDong_code()),
				   writerEq(cond.getWriter()),
				   openYnEq(cond.getOpenYn()))
			.orderBy(board.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = result.size() > pageable.getPageSize();
		if (hasNext) {
			result.remove(result.size() -1);
		}

		// 댓글 최신 1개 조회
		List<Long> boardIds = result.stream().map(BoardDetailDto::getBoardId).toList();
		Map<Long, CommentDetailDto> commentMap =
			queryFactory.select(
				new QCommentDetailDto(
					comment.id,
					comment.board.id,
					comment.member.id,
					comment.content,
					comment.createdDate,
					comment.modifiedDate
				)
			)
			.from(comment)
			.where(comment.board.id.in(boardIds))
			.orderBy(comment.id.desc())
			.fetch()
			.stream()
			.collect(Collectors.toMap(
				CommentDetailDto::getBoardId, // key
				commentDetailDto -> commentDetailDto, // value
				(boardId1, boardId2) -> boardId1) // 중복제거 조건
			);

		result.forEach(boardDetailDto -> {
			CommentDetailDto commentDetailDto = commentMap.get(boardDetailDto.getBoardId());
			boardDetailDto.setCommentDetailDto(commentDetailDto);
		});

		return new SliceImpl<>(result, pageable, hasNext);
	}

	private BooleanExpression sidoCodeEq(String sidoCodeCond) {
		return !sidoCodeCond.isEmpty() ? board.sido_code.eq(sidoCodeCond) : null;
	}

	private BooleanExpression sigoonCodeEq(String sigoonCodeCond) {
		return !sigoonCodeCond.isEmpty() ? board.sigoon_code.eq(sigoonCodeCond) : null;
	}

	private BooleanExpression dongCodeEq(String dongCodeCond) {
		return !dongCodeCond.isEmpty() ? board.dong_code.eq(dongCodeCond) : null;
	}

	private BooleanExpression writerEq(String writerCond) {
		return !writerCond.isEmpty() ? board.writer.eq(writerCond) : null;
	}

	private BooleanExpression openYnEq(String openYnCond) {
		return !openYnCond.isEmpty() ? board.openYn.eq(openYnCond.toUpperCase()) : board.openYn.eq("Y");
	}
}
