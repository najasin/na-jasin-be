package com.najasin.domain.character.repository;

import com.najasin.domain.character.entity.CharacterSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSetRepository extends JpaRepository<CharacterSet, Long> {
}
