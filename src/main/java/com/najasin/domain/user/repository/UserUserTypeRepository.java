package com.najasin.domain.user.repository;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.entity.userType.UserUserTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserUserTypeRepository extends JpaRepository<UserUserType, UserUserTypeId> {
    List<UserUserType> findAllByUser(User user);
}
