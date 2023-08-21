package com.najasin.service;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.repository.UserTypeRepository;
import com.najasin.domain.user.service.UserTypeService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserTypeServiceTest {
	@InjectMocks
	private UserTypeService userTypeService;

	@Mock
	private UserTypeRepository userTypeRepository;

	private UserType mockUserType;
	private String userTypeName;

	@BeforeEach
	void setup() {
		userTypeName = "test";
		mockUserType = new UserType(userTypeName);
	}

	@AfterEach
	void cleanup() {
		userTypeRepository.deleteAll();;
	}

	@Test
	@DisplayName("타입 이름으로 UserType을 찾는다.")
	void findByNameSuccess() {
		// given
		given(userTypeRepository.findByName(userTypeName))
			.willReturn(Optional.of(mockUserType));

		// when
		UserType userType = userTypeService.findByName(userTypeName);

		// then
		assertEquals(mockUserType, userType);
	}

	@Test
	@DisplayName("타입 이름으로 UserType을 찾는 데 실패한다")
	void findByNameFail() {
		// given
		given(userTypeRepository.findByName(userTypeName))
			.willReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundException.class, () -> {
			userTypeService.findByName(userTypeName);
		});
	}
}
