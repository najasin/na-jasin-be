package com.najasin.domain.userUserType.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.service.UserTypeService;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUserTypeService {
	private final UserTypeService userTypeService;
	private final UserUserTypeRepository userUserTypeRepository;

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
