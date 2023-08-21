package com.najasin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.repository.BodyRepository;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.repository.CharacterSetRepository;
import com.najasin.domain.keyword.dto.KeywordDTO;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.repository.ExpressionRepository;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.character.repository.FaceRepository;
import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.service.UserKeywordService;
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
import com.najasin.global.util.RedisBlackListUtil;
import com.najasin.security.model.OAuth2Request;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private CharacterSetRepository characterSetRepository;
	@Mock
	private FaceRepository faceRepository;
	@Mock
	private BodyRepository bodyRepository;
	@Mock
	private ExpressionRepository expressionRepository;
	@Mock
	private UserKeywordService userKeywordService;

	@Mock
	private RedisBlackListUtil redisBlackListUtil;

	private String mockId;
	private Oauth2Entity mockOauth2Entity;
	private User mockUser;
	private OAuth2Request request;
	private Face mockFace;
	private Body mockBody;
	private Expression mockExpression;
	private CharacterSet mockCS;
	private Long mockKeywordId;
	private Keyword mockKeyword;

	private UserKeyword prevUserKeyword;
	private UserKeyword mockUK;
	private int mockPercent;

	private List<KeywordDTO> keywordDTOs = new ArrayList<>();

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
		mockKeywordId = 123456789L;
		mockKeyword = new Keyword(mockKeywordId, "키워드");
		mockPercent = 50;
		mockUK = new UserKeyword(mockUser, mockKeyword, mockPercent);
		mockFace = new Face(123456789L, "mockFace", "show_url", "layout_url");
		mockBody = new Body(123456789L, "mockBody", "show_url", "layout_url");
		mockExpression = new Expression(123456789L, "mockBody", "show_url", "layout_url");
		mockCS = new CharacterSet(123456789L, "mockBody", "show_url");
		keywordDTOs.add(new KeywordDTO(123456789L, 30));
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
	@DisplayName("로그아웃을 수행한다.")
	void logout() {
		// given
		String accessToken = "";
		String refreshToken = "";
		doNothing().when(redisBlackListUtil).setBlackList(anyString(), anyString(), anyInt());

		// then
		assertDoesNotThrow(() -> userService.logout(accessToken, refreshToken));
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
	@DisplayName("유저 닉네임을 수정한다")
	void updateNickname() {
		//given
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		//when
		userService.updateNickname(mockUser.getId(), "새로운 닉네임");
		//then
		assertEquals(mockUser.getNickname(), "새로운 닉네임");
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
