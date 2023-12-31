package com.najasin.domain.user.entity.userType;

import com.najasin.domain.user.entity.User;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUserTypeId implements Serializable {
    private User user;
    private UserType userType;
}
