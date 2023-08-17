package com.najasin.domain.userUserType.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;

@Repository
public interface UserUserTypeRepository extends JpaRepository<UserUserType, UserUserTypeId> {
}
