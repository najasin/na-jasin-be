package com.najasin.domain.userType.entity;

import com.najasin.domain.userUserType.entity.UserUserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Long id;

    @Column(name = "user_type_name")
    private String name;

    @OneToMany(mappedBy = "userType")
    private List<UserUserType> userUserTypes;

    @Builder
    public UserType(String name) {
        this.name = name;
    }
}
