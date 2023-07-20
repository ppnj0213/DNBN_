package com.dnbn.back.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScrapbook is a Querydsl query type for Scrapbook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScrapbook extends EntityPathBase<Scrapbook> {

    private static final long serialVersionUID = -1410365453L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScrapbook scrapbook = new QScrapbook("scrapbook");

    public final QBoard board;

    public final com.dnbn.back.member.entity.QMember member;

    public QScrapbook(String variable) {
        this(Scrapbook.class, forVariable(variable), INITS);
    }

    public QScrapbook(Path<? extends Scrapbook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScrapbook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScrapbook(PathMetadata metadata, PathInits inits) {
        this(Scrapbook.class, metadata, inits);
    }

    public QScrapbook(Class<? extends Scrapbook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new com.dnbn.back.member.entity.QMember(forProperty("member")) : null;
    }

}

