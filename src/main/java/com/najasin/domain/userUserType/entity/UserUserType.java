package com.najasin.domain.userUserType.entity;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userType.entity.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_userType")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(UserUserTypeId.class)
public class UserUserType {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id", insertable = false, updatable = false)
    private UserType userType;
}
