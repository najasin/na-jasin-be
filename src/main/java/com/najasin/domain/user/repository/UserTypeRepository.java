package com.najasin.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.najasin.domain.user.entity.userType.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	Optional<UserType> findByName(String name);
}
