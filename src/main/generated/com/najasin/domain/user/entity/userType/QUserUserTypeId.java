package com.najasin.domain.user.entity.userType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserUserTypeId is a Querydsl query type for UserUserTypeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserUserTypeId extends BeanPath<UserUserTypeId> {

    private static final long serialVersionUID = -2077776465L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserUserTypeId userUserTypeId = new QUserUserTypeId("userUserTypeId");

    public final com.najasin.domain.user.entity.QUser user;

    public final QUserType userType;

    public QUserUserTypeId(String variable) {
        this(UserUserTypeId.class, forVariable(variable), INITS);
    }

    public QUserUserTypeId(Path<? extends UserUserTypeId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserUserTypeId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserUserTypeId(PathMetadata metadata, PathInits inits) {
        this(UserUserTypeId.class, metadata, inits);
    }

    public QUserUserTypeId(Class<? extends UserUserTypeId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
        this.userType = inits.isInitialized("userType") ? new QUserType(forProperty("userType")) : null;
    }

}

