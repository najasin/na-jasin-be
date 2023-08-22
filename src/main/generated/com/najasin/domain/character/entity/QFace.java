package com.najasin.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFace is a Querydsl query type for Face
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFace extends EntityPathBase<Face> {

    private static final long serialVersionUID = 1473660038L;

    public static final QFace face = new QFace("face");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath layout_url = createString("layout_url");

    public final StringPath name = createString("name");

    public final StringPath show_url = createString("show_url");

    public QFace(String variable) {
        super(Face.class, forVariable(variable));
    }

    public QFace(Path<? extends Face> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFace(PathMetadata metadata) {
        super(Face.class, metadata);
    }

}

