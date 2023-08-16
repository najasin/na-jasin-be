package com.najasin.domain.userUserType.entity;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userType.entity.UserType;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserUserTypeId implements Serializable {
    private User user;
    private UserType userType;
}
