package com.najasin.domain.user.repository;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.entity.userType.UserUserTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserUserTypeRepository extends JpaRepository<UserUserType, UserUserTypeId> {
    Optional<UserUserType> findByUserIdAndUserTypeName(String userId, String userTypeName);
    List<UserUserType> findAllByUser(User user);
}
