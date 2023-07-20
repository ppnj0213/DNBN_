package com.dnbn.back.board.model;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dnbn.back.board.model.QBoardDetailDto is a Querydsl Projection type for BoardDetailDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardDetailDto extends ConstructorExpression<BoardDetailDto> {

    private static final long serialVersionUID = -318506703L;

    public QBoardDetailDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> memberId, com.querydsl.core.types.Expression<String> sido_code, com.querydsl.core.types.Expression<String> sigoon_code, com.querydsl.core.types.Expression<String> dong_code, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(BoardDetailDto.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, id, memberId, sido_code, sigoon_code, dong_code, content, writer, createdDate);
    }

}

