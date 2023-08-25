package com.najasin.global.audit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditEntity is a Querydsl query type for AuditEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAuditEntity extends BeanPath<AuditEntity> {

    private static final long serialVersionUID = -953092435L;

    public static final QAuditEntity auditEntity = new QAuditEntity("auditEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QAuditEntity(String variable) {
        super(AuditEntity.class, forVariable(variable));
    }

    public QAuditEntity(Path<? extends AuditEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditEntity(PathMetadata metadata) {
        super(AuditEntity.class, metadata);
    }

}

