package com.dnbn.back.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyRegion is a Querydsl query type for MyRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyRegion extends EntityPathBase<MyRegion> {

    private static final long serialVersionUID = -822237613L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyRegion myRegion = new QMyRegion("myRegion");

    public final StringPath dong_code = createString("dong_code");

    public final StringPath dong_name = createString("dong_name");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mainRegionYn = createString("mainRegionYn");

    public final QMember member;

    public final StringPath sido_code = createString("sido_code");

    public final StringPath sido_name = createString("sido_name");

    public final StringPath sigoon_code = createString("sigoon_code");

    public final StringPath sigoon_name = createString("sigoon_name");

    public QMyRegion(String variable) {
        this(MyRegion.class, forVariable(variable), INITS);
    }

    public QMyRegion(Path<? extends MyRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyRegion(PathMetadata metadata, PathInits inits) {
        this(MyRegion.class, metadata, inits);
    }

    public QMyRegion(Class<? extends MyRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

