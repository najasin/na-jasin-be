package com.najasin.domain.character.characterset.repository;

import com.najasin.domain.character.characterset.entity.CharacterSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSetRepository extends JpaRepository<CharacterSet, Long> {
}
