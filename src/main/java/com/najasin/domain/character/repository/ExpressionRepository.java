package com.najasin.domain.character.repository;

import com.najasin.domain.character.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
