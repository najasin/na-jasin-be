package com.najasin.domain.manual.entity.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentId is a Querydsl query type for CommentId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCommentId extends BeanPath<CommentId> {

    private static final long serialVersionUID = 1945125163L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentId commentId = new QCommentId("commentId");

    public final com.najasin.domain.manual.entity.question.QQuestion question;

    public final com.najasin.domain.user.entity.QUser user;

    public QCommentId(String variable) {
        this(CommentId.class, forVariable(variable), INITS);
    }

    public QCommentId(Path<? extends CommentId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentId(PathMetadata metadata, PathInits inits) {
        this(CommentId.class, metadata, inits);
    }

    public QCommentId(Class<? extends CommentId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new com.najasin.domain.manual.entity.question.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

