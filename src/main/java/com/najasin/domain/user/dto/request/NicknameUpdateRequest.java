package com.najasin.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NicknameUpdateRequest(@NotBlank String nickname) {
}
