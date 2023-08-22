package com.najasin.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserTypeUpdateRequest(@NotBlank String userType) {
}
