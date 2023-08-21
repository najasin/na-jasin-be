package com.najasin.domain.user.entity.userType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserUserType is a Querydsl query type for UserUserType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserUserType extends EntityPathBase<UserUserType> {

    private static final long serialVersionUID = -158586508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserUserType userUserType = new QUserUserType("userUserType");

    public final com.najasin.domain.character.entity.QBody body;

    public final com.najasin.domain.character.entity.QExpression expression;

    public final com.najasin.domain.character.entity.QFace face;

    public final StringPath nickname = createString("nickname");

    public final com.najasin.domain.character.entity.QCharacterSet set;

    public final com.najasin.domain.user.entity.QUser user;

    public final QUserType userType;

    public QUserUserType(String variable) {
        this(UserUserType.class, forVariable(variable), INITS);
    }

    public QUserUserType(Path<? extends UserUserType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserUserType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserUserType(PathMetadata metadata, PathInits inits) {
        this(UserUserType.class, metadata, inits);
    }

    public QUserUserType(Class<? extends UserUserType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.body = inits.isInitialized("body") ? new com.najasin.domain.character.entity.QBody(forProperty("body")) : null;
        this.expression = inits.isInitialized("expression") ? new com.najasin.domain.character.entity.QExpression(forProperty("expression")) : null;
        this.face = inits.isInitialized("face") ? new com.najasin.domain.character.entity.QFace(forProperty("face")) : null;
        this.set = inits.isInitialized("set") ? new com.najasin.domain.character.entity.QCharacterSet(forProperty("set")) : null;
        this.user = inits.isInitialized("user") ? new com.najasin.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
        this.userType = inits.isInitialized("userType") ? new QUserType(forProperty("userType")) : null;
    }

}

