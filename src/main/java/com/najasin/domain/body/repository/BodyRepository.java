package com.najasin.domain.body.repository;

import com.najasin.domain.body.entity.Body;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyRepository extends JpaRepository<Body, Long> {
}
