package com.najasin.domain.user.entity.userType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserType is a Querydsl query type for UserType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserType extends EntityPathBase<UserType> {

    private static final long serialVersionUID = 72279689L;

    public static final QUserType userType = new QUserType("userType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<UserUserType, QUserUserType> userUserTypes = this.<UserUserType, QUserUserType>createList("userUserTypes", UserUserType.class, QUserUserType.class, PathInits.DIRECT2);

    public QUserType(String variable) {
        super(UserType.class, forVariable(variable));
    }

    public QUserType(Path<? extends UserType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserType(PathMetadata metadata) {
        super(UserType.class, metadata);
    }

}

