package com.najasin.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class UserInfoResponse {
    public String userId;
    public String userType;
}
