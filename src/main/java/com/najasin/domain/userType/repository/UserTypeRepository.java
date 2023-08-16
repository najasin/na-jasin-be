package com.najasin.domain.userType.repository;

import com.najasin.domain.userType.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
