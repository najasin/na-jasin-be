package com.najasin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.test.util.ReflectionTestUtils;

import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.user.service.UserService;
import com.najasin.security.model.OAuth2Request;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private String mockId;
	private Oauth2Entity mockOauth2Entity;
	private User mockUser;
	private OAuth2Request request;

	@BeforeEach
	public void setup() {
		mockId = "test-uuid";
		mockOauth2Entity = Oauth2Entity.builder()
			.provider(Provider.KAKAO)
			.providerId("test-provider-id")
			.providerUsername("test-provider-nickname")
			.email("test@kakao.com")
			.build();
		mockUser = new User(mockId, mockOauth2Entity);
		request = OAuth2Request.builder()
			.provider(Provider.KAKAO)
			.providerId("test-provider-id")
			.name("test-provider-nickname")
			.email("test@kakao.com")
			.build();
	}

	@AfterEach
	public void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("새로운 유저를 저장한다.")
	void save() {
		// given
		given(userRepository.save(any()))
			.willReturn(mockUser);

		// when
		User saveUser = userService.save(mockOauth2Entity);

		// then
		assertEquals(saveUser, mockUser);
	}

	@Test
	@DisplayName("해당 id의 유저를 조회한다")
	void findById() {
		// given
		ReflectionTestUtils.setField(mockUser, "id", mockId);
		given(userRepository.findById(anyString()))
			.willReturn(Optional.of(mockUser));

		// when
		User findUser = userService.findById(mockId);

		//then
		assertEquals(mockUser, findUser);
	}

	@Test
	@DisplayName("유저 조회를 실패한다")
	void findByIdFail() {
		// given
		given(userRepository.findById(anyString()))
			.willReturn(Optional.empty());

		//then
		assertThrows(EntityNotFoundException.class, () -> userService.findById(mockId));
	}

	@Test
	@DisplayName("새로운 유저일 경우 저장한다.")
	void saveIfNewUserSuccess() {
		// given
		given(userRepository.findUserByOauth2EntityProviderId(anyString()))
			.willReturn(Optional.empty());
		given(userRepository.save(any()))
			.willReturn(mockUser);

		// when
		User saveUser = userService.saveIfNewUser(request);

		// then
		assertEquals(mockUser, saveUser);
	}

	@Test
	@DisplayName("만약 유저가 존재하는 경우 기존 데이터를 리턴한다.")
	void saveIfNewUserFail() {
		// given
		given(userRepository.findUserByOauth2EntityProviderId(anyString()))
			.willReturn(Optional.of(mockUser));

		// when
		User existUser = userService.saveIfNewUser(request);

		// then
		assertEquals(mockUser, existUser);
	}

	@Test
	@DisplayName("UUID를 생성한다.")
	void generateUUID() {
		// given
		given(userRepository.findById(anyString()))
			.willReturn(Optional.empty());

		// when
		String uuid = userService.generateUUID();

		// then
		assertNotEquals(uuid, mockId);
	}
}
