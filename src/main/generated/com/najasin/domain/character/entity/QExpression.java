package com.najasin.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExpression is a Querydsl query type for Expression
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpression extends EntityPathBase<Expression> {

    private static final long serialVersionUID = 2121727489L;

    public static final QExpression expression = new QExpression("expression");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath layout_url = createString("layout_url");

    public final StringPath name = createString("name");

    public final StringPath show_url = createString("show_url");

    public QExpression(String variable) {
        super(Expression.class, forVariable(variable));
    }

    public QExpression(Path<? extends Expression> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExpression(PathMetadata metadata) {
        super(Expression.class, metadata);
    }

}

