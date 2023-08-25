package com.najasin.domain.character.repository;

import com.najasin.domain.character.entity.Face;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceRepository extends JpaRepository<Face, Long> {
}
