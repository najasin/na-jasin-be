package com.najasin.domain.manual.entity.userKeyword;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserKeywordId is a Querydsl query type for UserKeywordId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserKeywordId extends BeanPath<UserKeywordId> {

    private static final long serialVersionUID = -1217772247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserKeywordId userKeywordId = new QUserKeywordId("userKeywordId");

    public final com.najasin.domain.manual.entity.keyword.QKeyword keyword;

    public final com.najasin.domain.user.entity.QUser user;

    public QUserKeywordId(String variable) {
        this(UserKeywordId.class, forVariable(variable), INITS);
    }

    public QUserKeywordId(Path<? extends UserKeywordId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserKeywordId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserKeywordId(PathMetadata metadata, PathInits inits) {
        this(UserKeywordId.class, metadata, inits);
    }

    public QUserKeywordId(Class<? extends UserKeywordId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new com.najasin.domain.manual.entity.keyword.QKeyword(forProperty("keyword")) : null;
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

