package com.najasin.domain.user.service;

import com.najasin.global.advice.NotFoundErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.repository.UserTypeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeService {
	private final UserTypeRepository userTypeRepository;

	@Transactional(readOnly = true)
	public UserType findByName(String name) {
		return userTypeRepository.findByName(name).orElseThrow(()->new EntityNotFoundException(NotFoundErrorMessages.USER_TYPE_NOT_FOUND));
	}
}
