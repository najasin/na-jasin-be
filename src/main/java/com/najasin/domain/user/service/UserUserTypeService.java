package com.najasin.domain.user.service;

import com.najasin.domain.character.repository.BodyRepository;
import com.najasin.domain.character.repository.CharacterSetRepository;
import com.najasin.domain.character.repository.ExpressionRepository;
import com.najasin.domain.character.repository.FaceRepository;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.repository.UserTypeRepository;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.repository.UserUserTypeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUserTypeService {
	private final UserUserTypeRepository userUserTypeRepository;
	private final UserTypeService userTypeService;

	@Transactional
	public String updateUserType(User user, String userTypeName) {
		UserType userType = userTypeService.findByName(userTypeName);

		if (!checkAlreadyExist(user.getUserUserTypes(), userType)) {
			save(user, userType);
		}

		user.updateLastUserType(userType);
		return userTypeName;
	}

	@Transactional
	public UserUserType save(User user, UserType userType) {
		return userUserTypeRepository.save(new UserUserType(user, userType));
	}

	private boolean checkAlreadyExist(List<UserUserType> userUserTypes, UserType userType) {
		return userUserTypes.stream().map(UserUserType::getUserType).toList().contains(userType);
	}
}
