package com.najasin.security.oauth.common.handler;

import static java.util.Objects.*;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.service.UserService;
import com.najasin.security.jwt.dto.JwtToken;
import com.najasin.security.jwt.service.JwtGenerateService;
import com.najasin.security.model.PrincipalUser;
import com.najasin.security.oauth.dto.response.OAuth2Response;
import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtGenerateService jwtGenerateService;
	private final UserService userService;

	@Value("${client.url}")
	private String clientUrl;

	@Value("${client.endpoint}")
	private String redirectEndPoint;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		PrincipalUser principalUser = (PrincipalUser)authentication.getPrincipal();
		User user = principalUser.getUser();

		JwtToken jwtToken = jwtGenerateService.createJwtToken(principalUser);
		String userId = user.getId();
		String userType = isNull(user.getLastUserType()) ? "" : user.getLastUserType().getName();

		OAuth2Response oAuth2Response = OAuth2Response.builder()
			.accessToken(jwtToken.accessToken())
			.refreshToken(jwtToken.refreshToken())
			.userId(userId)
			.userType(userType)
			.build();

		Gson gson = new Gson();
		String jsonResponse = gson.toJson(oAuth2Response);

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);

		try (PrintWriter out = response.getWriter()) {
			out.print(jsonResponse);
			out.flush();
		}
	}
}
