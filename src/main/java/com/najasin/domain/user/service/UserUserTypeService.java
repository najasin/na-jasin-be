package com.najasin.domain.user.service;

import static java.util.Objects.*;

import com.najasin.domain.character.service.CharacterService;
import com.najasin.domain.manual.answer.entity.Answer;
import com.najasin.domain.manual.answer.service.AnswerService;
import com.najasin.domain.manual.dto.param.ManualCharacterItems;
import com.najasin.domain.manual.userKeyword.entity.UserKeyword;
import com.najasin.domain.manual.userKeyword.service.UserKeywordService;
import com.najasin.domain.user.dto.param.MyAnswerParam;
import com.najasin.domain.user.dto.param.MyKeywordPercentParam;
import com.najasin.domain.user.dto.response.MyPageResponse;
import com.najasin.domain.user.dto.response.UserTypeUpdateResponse;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.repository.UserUserTypeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUserTypeService {
	private final UserUserTypeRepository userUserTypeRepository;
	private final UserTypeService userTypeService;
	private final CharacterService characterService;
	private final AnswerService answerService;
	private final UserKeywordService userKeywordService;
	@Value("${base-image}")
	private String baseImage;

	@Transactional
	public UserTypeUpdateResponse updateUserType(User user, String userTypeName) {
		UserType userType = userTypeService.findByName(userTypeName);
		user.updateLastUserType(userType);

		if (!checkAlreadyExist(user.getUserUserTypes(), userType)) {
			return UserTypeUpdateResponse.of(userTypeName, Boolean.FALSE);
		}

		return UserTypeUpdateResponse.of(userTypeName, Boolean.TRUE);
	}

	@Transactional
	public UserUserType save(User user, String userTypeName, String nickname) {
		UserType userType = userTypeService.findByName(userTypeName);

		return userUserTypeRepository.save(new UserUserType(user, userType, nickname));
	}

	@Transactional
	public void updateNickname(UserUserType userUserType, String nickname) {
		userUserType.updateNickname(nickname);
	}

	@Transactional
	public void updateCharacter(UserUserType userUserType, ManualCharacterItems items) {
		if (!isNull(items.set())) {
			userUserType.updateCharacter(characterService.findCharacterSetById(items.set()));
		} else {
			userUserType.updateCharacter(
				characterService.findFaceById(items.face()),
				characterService.findBodyById(items.body()),
				characterService.findExpressionById(items.expression()));
		}
	}

	@Transactional(readOnly = true)
	public MyPageResponse getMyPage(User user, String userType, String userId) {
		List<UserType> userTypes =
			isNull(user) ? null : user.getUserUserTypes().stream().map(UserUserType::getUserType).toList();
		UserUserType userUserType = findByUserIdAndUserTypeName(userId, userType);
		List<MyAnswerParam> answers = answerService.findByUserIdAndUserType(userId, userType)
			.stream()
			.map(Answer::toMyAnswerParam)
			.toList();
		List<MyKeywordPercentParam> percents = userKeywordService.findByUserId(userId)
			.stream()
			.map(UserKeyword::toMyKeywordPercentParam)
			.toList();

		return MyPageResponse.builder()
			.userTypes(isNull(userTypes) ? null : userTypes.stream().map(UserType::getName).toList())
			.nickname(userUserType.getNickname())
			.baseImage(baseImage)
			.characterItems(userUserType.toMyCharacterItemsParam())
			.myManualQAPair(answers)
			.originKeywordPercents(percents)
			.isOwner(!isNull(user))
			.build();
	}

	@Transactional(readOnly = true)
	public UserUserType findByUserIdAndUserTypeName(String userId, String userTypeName) {
		return userUserTypeRepository.findByUserIdAndUserTypeName(userId, userTypeName)
			.orElseThrow(EntityNotFoundException::new);
	}

	private boolean checkAlreadyExist(List<UserUserType> userUserTypes, UserType userType) {
		return userUserTypes.stream().map(UserUserType::getUserType).toList().contains(userType);
	}
}
