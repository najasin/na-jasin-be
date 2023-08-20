package com.najasin.domain.user.service;

import java.util.ArrayList;
import java.util.UUID;

import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.comment.service.CommentService;
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
	private final CharacterSetRepository characterSetRepository;
	private final FaceRepository faceRepository;
	private final BodyRepository bodyRepository;
	private final ExpressionRepository expressionRepository;
	private final UserKeywordService userKeywordService;
	private final CommentService commentService;
	private final RedisBlackListUtil redisBlackListUtil;


	@Transactional
	public User saveIfNewUser(OAuth2Request request) {
		return userRepository.findUserByOauth2EntityProviderId(request.providerId()).orElseGet(
				() -> save(request.toOauth2Entity()));
	}

	@Transactional
	public User save(Oauth2Entity oauth2Entity) {
		return userRepository.save(new User(generateUUID(), oauth2Entity));
	}

	@Transactional(readOnly = true)
	public User findById(String id) {
		System.out.println(id);
		return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public void logout(String accessToken, String refreshToken) {
		redisBlackListUtil.setBlackList(accessToken, "accessToken", 7);
		redisBlackListUtil.setBlackList(refreshToken, "refreshToken", 7);
	}

	@Transactional
	public User updateNickname(String id, String nickname) {
		User user = this.findById(id);
		user.updateNickname(nickname);
		return user;
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
