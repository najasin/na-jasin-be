package com.najasin.domain.character.face.repository;

import com.najasin.domain.character.face.entity.Face;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceRepository extends JpaRepository<Face, Long> {
}
