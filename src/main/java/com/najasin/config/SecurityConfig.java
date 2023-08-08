package com.najasin.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	//  private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf((AbstractHttpConfigurer::disable))
			.formLogin((AbstractHttpConfigurer::disable))
			.headers(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(setAuthorizeHttpRequests())
			.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			//        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> setAuthorizeHttpRequests() {
		return requests ->
			requests.requestMatchers(
					new AntPathRequestMatcher("/swagger-ui/index.html"),
					new AntPathRequestMatcher("/api/auth"),
					new AntPathRequestMatcher("/login/**")
				).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/api/**")).hasAnyRole("ADMIN", "MEMBER")
				.anyRequest().authenticated();
	}
}