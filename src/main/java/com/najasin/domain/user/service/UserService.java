package com.najasin.domain.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.najasin.domain.body.entity.Body;
import com.najasin.domain.body.repository.BodyRepository;
import com.najasin.domain.characterset.entity.CharacterSet;
import com.najasin.domain.characterset.repository.CharacterSetRepository;
import com.najasin.domain.dto.CharacterDTO;
import com.najasin.domain.dto.KeywordDTO;
import com.najasin.domain.expression.entity.Expression;
import com.najasin.domain.expression.repository.ExpressionRepository;
import com.najasin.domain.face.entity.Face;
import com.najasin.domain.face.repository.FaceRepository;
import com.najasin.domain.user.entity.enums.Role;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.body.entity.Body;
import com.najasin.domain.body.repository.BodyRepository;
import com.najasin.domain.characterset.entity.CharacterSet;
import com.najasin.domain.characterset.repository.CharacterSetRepository;
import com.najasin.domain.comment.service.CommentService;
import com.najasin.domain.dto.CharacterDTO;
import com.najasin.domain.dto.KeywordDTO;
import com.najasin.domain.expression.entity.Expression;
import com.najasin.domain.expression.repository.ExpressionRepository;
import com.najasin.domain.face.entity.Face;
import com.najasin.domain.face.repository.FaceRepository;
import com.najasin.domain.user.entity.enums.Role;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.service.UserKeywordService;
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
		return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public void logout(String accessToken, String refreshToken) {
		redisBlackListUtil.setBlackList(accessToken, "accessToken", 7);
		redisBlackListUtil.setBlackList(refreshToken, "refreshToken", 7);
	}



	@Transactional
	public User deleteAnswers(String id) {
		User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		user.updateAnswer(new ArrayList<>());
		return userRepository.save(user);
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
