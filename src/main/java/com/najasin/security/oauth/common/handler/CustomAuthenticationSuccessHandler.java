package com.najasin.security.oauth.common.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.najasin.security.jwt.dto.JwtToken;
import com.najasin.security.jwt.service.JwtGenerateService;
import com.najasin.security.model.PrincipalUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtGenerateService jwtGenerateService;

    @Value("${client.url}")
    private String clientUrl;

    @Value("${client.endpoint}")
    private String redirectEndPoint;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        StringBuilder sb = new StringBuilder();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        JwtToken jwtToken = jwtGenerateService.createJwtToken(principalUser);
        sb.append(clientUrl)
            .append("/").append(redirectEndPoint)
            .append("?accessToken=").append(jwtToken.accessToken())
            .append("&refreshToken=").append(jwtToken.refreshToken());
        getRedirectStrategy().sendRedirect(request, response, sb.toString());
    }
}
