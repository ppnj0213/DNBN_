package com.dnbn.back.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1247573523L;

    public static final QMember member = new QMember("member1");

    public final com.dnbn.back.common.entity.QBaseTimeEntity _super = new com.dnbn.back.common.entity.QBaseTimeEntity(this);

    public final ListPath<com.dnbn.back.board.entity.Board, com.dnbn.back.board.entity.QBoard> boards = this.<com.dnbn.back.board.entity.Board, com.dnbn.back.board.entity.QBoard>createList("boards", com.dnbn.back.board.entity.Board.class, com.dnbn.back.board.entity.QBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<MyRegion, QMyRegion> myRegions = this.<MyRegion, QMyRegion>createList("myRegions", MyRegion.class, QMyRegion.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath userId = createString("userId");

    public final StringPath userPw = createString("userPw");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

