package com.najasin.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBody is a Querydsl query type for Body
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBody extends EntityPathBase<Body> {

    private static final long serialVersionUID = 1473554379L;

    public static final QBody body = new QBody("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath layout_url = createString("layout_url");

    public final StringPath name = createString("name");

    public final StringPath show_url = createString("show_url");

    public QBody(String variable) {
        super(Body.class, forVariable(variable));
    }

    public QBody(Path<? extends Body> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBody(PathMetadata metadata) {
        super(Body.class, metadata);
    }

}

