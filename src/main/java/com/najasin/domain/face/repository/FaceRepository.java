package com.najasin.domain.face.repository;

import com.najasin.domain.face.entity.Face;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceRepository extends JpaRepository<Face, Long> {
}
