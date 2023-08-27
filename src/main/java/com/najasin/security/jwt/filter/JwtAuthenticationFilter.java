package com.najasin.security.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.najasin.security.jwt.exception.JwtBlackListException;
import com.najasin.security.jwt.exception.JwtExpirationException;
import com.najasin.security.jwt.exception.JwtNotSupportException;
import com.najasin.security.jwt.exception.JwtWrongException;
import com.najasin.security.jwt.exception.JwtWrongSignatureException;
import com.najasin.security.jwt.util.JwtValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_TAG = "Authorization";

    private final JwtValidator jwtValidator;
    private boolean filtered = false;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = Optional.ofNullable(getTokensFromHeader(request));

        token.ifPresent(
                t -> {
                    Authentication authentication = null;
                    try {
                        authentication = jwtValidator.getAuthentication(t);
                    } catch (JwtWrongSignatureException | JwtExpirationException | JwtNotSupportException |
                             JwtWrongException | JwtBlackListException e) {
                        handleJwtException(e, response);
                        return;
                    }
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
        //에러 발생시 doFilter이 실행 안되어야 하는 것 같은데 자꾸 실행되어서 임시 조치하였습니다.
        if (!filtered) {
            filterChain.doFilter(request, response);
        }
        filtered = false;
    }

    private String getTokensFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_TAG);
    }
    private void handleJwtException(Exception exception, HttpServletResponse response){
        filtered=true;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
