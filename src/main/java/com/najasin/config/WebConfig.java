package com.najasin.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.najasin.global.resovler.AccessTokenArgumentsResolver;
import com.najasin.global.resovler.RefreshTokenArgumentsResolver;
import com.najasin.global.resovler.UserArgumentsResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	@Value("${client.url}")
	private String clientUrl;

	private final AccessTokenArgumentsResolver accessTokenArgumentsResolver;
	private final RefreshTokenArgumentsResolver refreshTokenArgumentsResolver;
	private final UserArgumentsResolver userArgumentsResolver;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(clientUrl, "http://localhost:3000");
		registry.addMapping("/**")
			.allowedOrigins(clientUrl, "http://localhost:3000")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowCredentials(true);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.addAll(List.of(accessTokenArgumentsResolver, refreshTokenArgumentsResolver, userArgumentsResolver));
	}
}
