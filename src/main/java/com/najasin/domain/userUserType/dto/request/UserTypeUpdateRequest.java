package com.najasin.domain.userUserType.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserTypeUpdateRequest(@NotBlank String userType) {
}
