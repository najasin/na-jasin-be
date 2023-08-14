package com.najasin.domain.expression.repository;

import com.najasin.domain.expression.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
