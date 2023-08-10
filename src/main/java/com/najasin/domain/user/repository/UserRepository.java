package com.najasin.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.najasin.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findUserByOauth2EntityProviderId(String oauth2Entity_providerId);
}
