package com.dnbn.back.comment.model;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dnbn.back.comment.model.QCommentDetailDto is a Querydsl Projection type for CommentDetailDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentDetailDto extends ConstructorExpression<CommentDetailDto> {

    private static final long serialVersionUID = 1931599103L;

    public QCommentDetailDto(com.querydsl.core.types.Expression<Long> commentId, com.querydsl.core.types.Expression<Long> boardId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedDate) {
        super(CommentDetailDto.class, new Class<?>[]{long.class, long.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, commentId, boardId, content, createdDate, modifiedDate);
    }

}

