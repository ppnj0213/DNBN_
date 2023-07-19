package com.dnbn.back.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1479866081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.dnbn.back.common.entity.QBaseTimeEntity _super = new com.dnbn.back.common.entity.QBaseTimeEntity(this);

    public final ListPath<com.dnbn.back.comment.entity.Comment, com.dnbn.back.comment.entity.QComment> comments = this.<com.dnbn.back.comment.entity.Comment, com.dnbn.back.comment.entity.QComment>createList("comments", com.dnbn.back.comment.entity.Comment.class, com.dnbn.back.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath dong_code = createString("dong_code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> liked = createNumber("liked", Integer.class);

    public final com.dnbn.back.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath openYn = createString("openYn");

    public final StringPath sido_code = createString("sido_code");

    public final StringPath sigoon_code = createString("sigoon_code");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.dnbn.back.member.entity.QMember(forProperty("member")) : null;
    }

}

