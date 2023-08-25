package com.najasin.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOauth2Entity is a Querydsl query type for Oauth2Entity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOauth2Entity extends BeanPath<Oauth2Entity> {

    private static final long serialVersionUID = 1912136931L;

    public static final QOauth2Entity oauth2Entity = new QOauth2Entity("oauth2Entity");

    public final StringPath email = createString("email");

    public final EnumPath<com.najasin.domain.user.entity.enums.Provider> provider = createEnum("provider", com.najasin.domain.user.entity.enums.Provider.class);

    public final StringPath providerId = createString("providerId");

    public final StringPath providerUsername = createString("providerUsername");

    public QOauth2Entity(String variable) {
        super(Oauth2Entity.class, forVariable(variable));
    }

    public QOauth2Entity(Path<? extends Oauth2Entity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOauth2Entity(PathMetadata metadata) {
        super(Oauth2Entity.class, metadata);
    }

}

