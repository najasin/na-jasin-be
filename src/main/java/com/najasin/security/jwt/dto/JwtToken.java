package com.najasin.security.jwt.dto;

public record JwtToken(String accessToken, String refreshToken, String grantType) {

    public JwtToken {}
}
