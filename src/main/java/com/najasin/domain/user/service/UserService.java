package com.najasin.domain.user.service;

import java.util.UUID;

import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.repository.UserTypeRepository;
import com.najasin.global.advice.NotFoundErrorMessages;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.global.util.RedisBlackListUtil;
import com.najasin.security.model.OAuth2Request;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserTypeRepository userTypeRepository;
	private final RedisBlackListUtil redisBlackListUtil;

	@Transactional
	public User saveIfNewUser(OAuth2Request request) {
		return userRepository.findUserByOauth2EntityProviderId(request.providerId()).orElseGet(
			() -> save(request.toOauth2Entity()));
	}

	@Transactional
	public void updateLastUserType(User user, String lastUserType) {
		UserType userType = userTypeRepository.findByName(lastUserType).orElseThrow(EntityNotFoundException::new);
		user.updateLastUserType(userType);
		userRepository.save(user);
	}

	@Transactional
	public User save(Oauth2Entity oauth2Entity) {
		return userRepository.save(new User(generateUUID(), oauth2Entity));
	}

	@Transactional(readOnly = true)
	public User findById(String id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(NotFoundErrorMessages.USER_NOT_FOUND));
	}

	public void logout(String accessToken, String refreshToken) {
		redisBlackListUtil.setBlackList(accessToken, "accessToken", 7);
		redisBlackListUtil.setBlackList(refreshToken, "refreshToken", 7);
	}

	public String generateUUID() {
		String uuid = UUID.randomUUID().toString();

		while (checkDuplicatedUUID(uuid)) {
			uuid = UUID.randomUUID().toString();
		}

		return uuid;
	}

	public boolean checkDuplicatedUUID(String uuid) {
		return userRepository.findById(uuid).isPresent();
	}
}
