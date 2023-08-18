package com.najasin.domain.userUserType.repository;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserUserTypeRepository extends JpaRepository<UserUserType, UserUserTypeId> {
    List<UserUserType> findAllByUser(User user);
}
