package com.dnbn.back.board.repository;

import static com.dnbn.back.board.entity.QBoard.*;
import static com.dnbn.back.board.entity.QLike.*;
import static com.querydsl.jpa.JPAExpressions.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.board.model.BoardDetailDto;
import com.dnbn.back.board.model.BoardSearchCond;
import com.dnbn.back.board.model.QBoardDetailDto;
import com.querydsl.core.types.dsl.BooleanExpression;
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
	public Slice<BoardDetailDto> getBoardListWithSlice(Pageable pageable, BoardSearchCond cond) {
		List<BoardDetailDto> result =
			queryFactory.select(new QBoardDetailDto(
					board.id,
					board.member.id,
					board.sido_code,
					board.sigoon_code,
					board.dong_code,
					board.content,
					board.writer,
					board.createdDate,
					select(like.id.count())
						.from(like)
						.where(like.board.id.eq(board.id))
				))
				.from(board)
				.where(sidoCodeEq(cond.getSido_code()),
					   sigoonCodeEq(cond.getSigoon_code()),
					   dongCodeEq(cond.getDong_code()),
					   writerEq(cond.getWriter()))
				.orderBy(board.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize() + 1)
				.fetch();

		boolean hasNext = result.size() > pageable.getPageSize();
		if (hasNext) {
			result.remove(result.size() -1);
		}

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
}
