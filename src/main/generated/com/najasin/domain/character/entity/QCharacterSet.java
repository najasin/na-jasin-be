package com.najasin.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCharacterSet is a Querydsl query type for CharacterSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharacterSet extends EntityPathBase<CharacterSet> {

    private static final long serialVersionUID = 575177122L;

    public static final QCharacterSet characterSet = new QCharacterSet("characterSet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath layout_url = createString("layout_url");

    public final StringPath name = createString("name");

    public final StringPath show_url = createString("show_url");

    public QCharacterSet(String variable) {
        super(CharacterSet.class, forVariable(variable));
    }

    public QCharacterSet(Path<? extends CharacterSet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCharacterSet(PathMetadata metadata) {
        super(CharacterSet.class, metadata);
    }

}

