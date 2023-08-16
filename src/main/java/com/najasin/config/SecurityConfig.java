package com.najasin.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.najasin.security.jwt.filter.JwtAuthenticationFilter;
import com.najasin.security.oauth.service.CustomOAuth2UserService;
import com.najasin.security.oauth.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomOidcUserService customOidcUserService;
	private final AuthenticationSuccessHandler successHandler;
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf((AbstractHttpConfigurer::disable))
			.formLogin((AbstractHttpConfigurer::disable))
			.headers(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(setAuthorizeHttpRequests())
			.oauth2Login(setOAuth2Config())
			.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> setAuthorizeHttpRequests() {
		return requests ->
			requests.requestMatchers(
					new AntPathRequestMatcher("/swagger-ui/index.html"),
					new AntPathRequestMatcher("/api/auth"),
					new AntPathRequestMatcher("/auth2/**"),
					new AntPathRequestMatcher("/login/**")
				).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/api/**")).hasAnyRole("ADMIN", "MEMBER")
				.anyRequest().authenticated();
	}

	private Customizer<OAuth2LoginConfigurer<HttpSecurity>> setOAuth2Config() {
		return oauth ->
			oauth.authorizationEndpoint(o -> o.baseUri("/auth2/authorize"))
				.userInfoEndpoint(userInfoEndpointConfig ->
					userInfoEndpointConfig.userService(customOAuth2UserService)
						.oidcUserService(customOidcUserService))
				.successHandler(successHandler);
	}
}