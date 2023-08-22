package com.najasin.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1225049744L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.najasin.global.audit.QAuditEntity auditEntity;

    public final StringPath id = createString("id");

    public final com.najasin.domain.user.entity.userType.QUserType lastUserType;

    public final QOauth2Entity oauth2Entity;

    public final ListPath<com.najasin.domain.user.entity.enums.Role, EnumPath<com.najasin.domain.user.entity.enums.Role>> role = this.<com.najasin.domain.user.entity.enums.Role, EnumPath<com.najasin.domain.user.entity.enums.Role>>createList("role", com.najasin.domain.user.entity.enums.Role.class, EnumPath.class, PathInits.DIRECT2);

    public final ListPath<com.najasin.domain.user.entity.userType.UserUserType, com.najasin.domain.user.entity.userType.QUserUserType> userUserTypes = this.<com.najasin.domain.user.entity.userType.UserUserType, com.najasin.domain.user.entity.userType.QUserUserType>createList("userUserTypes", com.najasin.domain.user.entity.userType.UserUserType.class, com.najasin.domain.user.entity.userType.QUserUserType.class, PathInits.DIRECT2);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditEntity = inits.isInitialized("auditEntity") ? new com.najasin.global.audit.QAuditEntity(forProperty("auditEntity")) : null;
        this.lastUserType = inits.isInitialized("lastUserType") ? new com.najasin.domain.user.entity.userType.QUserType(forProperty("lastUserType")) : null;
        this.oauth2Entity = inits.isInitialized("oauth2Entity") ? new QOauth2Entity(forProperty("oauth2Entity")) : null;
    }

}

