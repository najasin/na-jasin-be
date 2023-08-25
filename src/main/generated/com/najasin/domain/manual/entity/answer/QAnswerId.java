package com.najasin.domain.manual.entity.answer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswerId is a Querydsl query type for AnswerId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAnswerId extends BeanPath<AnswerId> {

    private static final long serialVersionUID = 303692809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswerId answerId = new QAnswerId("answerId");

    public final com.najasin.domain.manual.entity.question.QQuestion question;

    public final com.najasin.domain.user.entity.QUser user;

    public QAnswerId(String variable) {
        this(AnswerId.class, forVariable(variable), INITS);
    }

    public QAnswerId(Path<? extends AnswerId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswerId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswerId(PathMetadata metadata, PathInits inits) {
        this(AnswerId.class, metadata, inits);
    }

    public QAnswerId(Class<? extends AnswerId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new com.najasin.domain.manual.entity.question.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

