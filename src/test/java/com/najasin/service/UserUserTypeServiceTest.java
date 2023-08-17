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

import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.service.UserTypeService;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;
import com.najasin.domain.userUserType.service.UserUserTypeService;

@ExtendWith(MockitoExtension.class)
public class UserUserTypeServiceTest {
	@InjectMocks
	private UserUserTypeService userUserTypeService;

	@Mock
	private UserTypeService userTypeService;

	@Mock
	private UserUserTypeRepository userUserTypeRepository;

	private User mockUser;
	private UserType mockUserType;
	private String userTypeName;
	private UserUserType mockUserUserType;

	@BeforeEach
	void setup() {
		Oauth2Entity oauth2Entity = Oauth2Entity.builder().build();
		userTypeName = "test";
		mockUser = new User("", oauth2Entity);
		mockUserType = new UserType(userTypeName);
		mockUserUserType = new UserUserType(mockUser, mockUserType);
	}

	@AfterEach
	void cleanup() {
		userUserTypeRepository.deleteAll();
	}

	@Test
	@DisplayName("유저와 유저 타입의 연관관계를 저장한다")
	void save() {
		// given
		given(userUserTypeRepository.save(any()))
			.willReturn(mockUserUserType);

		// when
		UserUserType userUserType = userUserTypeService.save(mockUser, mockUserType);

		// then
		assertEquals(userUserType, mockUserUserType);
	}

	@Test
	@DisplayName("유저의 유저 타입을 변경한다.")
	void updateUserType() {
		// given
		given(userTypeService.findByName(userTypeName))
			.willReturn(mockUserType);

		//when
		String updatedTypeName = userUserTypeService.updateUserType(mockUser, userTypeName);

		// then
		assertEquals(updatedTypeName, userTypeName);
	}
}
