package com.najasin.domain.userType.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeService {
	private final UserTypeRepository userTypeRepository;

	@Transactional(readOnly = true)
	public UserType findByName(String name) {
		return userTypeRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
	}
}
