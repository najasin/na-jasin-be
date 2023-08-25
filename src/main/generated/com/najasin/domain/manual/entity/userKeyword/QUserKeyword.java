package com.najasin.domain.manual.entity.userKeyword;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserKeyword is a Querydsl query type for UserKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserKeyword extends EntityPathBase<UserKeyword> {

    private static final long serialVersionUID = 1728339822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserKeyword userKeyword = new QUserKeyword("userKeyword");

    public final com.najasin.domain.manual.entity.keyword.QKeyword keyword;

    public final NumberPath<Integer> originPercent = createNumber("originPercent", Integer.class);

    public final NumberPath<Integer> othersCount = createNumber("othersCount", Integer.class);

    public final NumberPath<Integer> othersPercent = createNumber("othersPercent", Integer.class);

    public final com.najasin.domain.user.entity.QUser user;

    public QUserKeyword(String variable) {
        this(UserKeyword.class, forVariable(variable), INITS);
    }

    public QUserKeyword(Path<? extends UserKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserKeyword(PathMetadata metadata, PathInits inits) {
        this(UserKeyword.class, metadata, inits);
    }

    public QUserKeyword(Class<? extends UserKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new com.najasin.domain.manual.entity.keyword.QKeyword(forProperty("keyword")) : null;
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

