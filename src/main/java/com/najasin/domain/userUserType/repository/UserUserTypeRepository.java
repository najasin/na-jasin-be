package com.najasin.domain.userUserType.repository;

import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUserTypeRepository extends JpaRepository<UserUserType, UserUserTypeId> {

}
