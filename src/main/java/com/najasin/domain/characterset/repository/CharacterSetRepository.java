package com.najasin.domain.characterset.repository;

import com.najasin.domain.characterset.entity.CharacterSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSetRepository extends JpaRepository<CharacterSet, Long> {
}
