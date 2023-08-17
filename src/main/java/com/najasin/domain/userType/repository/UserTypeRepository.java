package com.najasin.domain.userType.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.najasin.domain.userType.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	Optional<UserType> findByName(String name);
}
