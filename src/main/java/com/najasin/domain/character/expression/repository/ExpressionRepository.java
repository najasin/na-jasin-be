package com.najasin.domain.character.expression.repository;

import com.najasin.domain.character.expression.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
