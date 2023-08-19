package com.najasin.service;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.CharacterItem;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.character.dto.CharacterItems;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userType.repository.UserTypeRepository;
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
	@Mock
	private UserRepository userRepository;
	@Mock
	private FaceRepository faceRepository;
	@Mock
	private BodyRepository bodyRepository;
	@Mock
	private ExpressionRepository expressionRepository;
	@Mock
	private CharacterSetRepository characterSetRepository;
	@Mock
	private UserTypeRepository userTypeRepository;

	private User mockUser;
	private UserType mockUserType;
	private String userTypeName;
	private UserUserType mockUserUserType;
	private List<Answer> mockAnswers;
	private Question question;
	private Face mockFace;
	private Body mockBody;
	private Expression mockExpression;
	private CharacterSet mockCharacterSet;


	private CharacterItems dto;

	@BeforeEach
	void setup() {
		Oauth2Entity oauth2Entity = Oauth2Entity.builder().build();
		userTypeName = "test";
		mockAnswers = new ArrayList<>();
		question = new Question(1L, "test question", QuestionType.FOR_USER, null, null);
		mockAnswers.add(new Answer(mockUser, question, "answer"));
		mockUser = User.builder()
				.answers(mockAnswers)
				.userUserTypes(new ArrayList<>())
				.build();
		mockUserType = new UserType(userTypeName);
		mockUserUserType = new UserUserType(mockUser, mockUserType);
		dto = new CharacterItems();
		dto.setBody(CharacterItem.builder()
				.id(2L)
				.build());
		dto.setFace(CharacterItem.builder()
				.id(1L)
				.build());
		dto.setExpression(null);
		dto.setSet(null);

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

	@Test
	@DisplayName("유저와 유저타입 관계를 받아온다.")
	public void getUserUserTypeById() {
		//given
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userTypeRepository.findByName(any())).willReturn(Optional.of(mockUserType));
		given(userUserTypeRepository.findById(any())).willReturn(Optional.ofNullable(mockUserUserType));
		//when
		UserUserType getUserUserType = userUserTypeService.getUserUserTypeById(mockUser.getId(), mockUserType.getName());
		//then
		assertEquals(mockUserUserType, getUserUserType);
	}

	@Test
	@DisplayName("유저아이디로 유저타입관계들을 가져온다")
	public void getUserUserTypesById() {
		//given
		List<UserUserType> list = new ArrayList<>();
		list.add(mockUserUserType);
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userUserTypeRepository.findAllByUser(any())).willReturn(list);
		//when
		List<UserUserType> getList = userUserTypeService.getUserUserTypesByUserId(mockUser.getId());
		//then
		assertEquals(getList, list);
	}

	@Test
	@DisplayName("유저 아이디와 타입과 질문 유형 별 질문 응답을 가져온다")
	public void getQAByUserIdAndUserTypeAndQuestionType(){
		//given
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userTypeRepository.findByName(any())).willReturn(Optional.of(mockUserType));
		given(userUserTypeRepository.findById(any())).willReturn(Optional.ofNullable(mockUserUserType));
		List<Page.QAPair> myQaParis = new ArrayList<>();
		myQaParis.add(new Page.QAPair(1L, "test question", "answer"));
		//when
		List<Page.QAPair> getQaPair = userUserTypeService.getQAByUserIdAndUserTypeForUser(mockUser.getId(), "JFF", QuestionType.FOR_USER);
		//then
		assertEquals(getQaPair.get(0).getQuestion(), myQaParis.get(0).getQuestion());
		assertEquals(getQaPair.get(0).getAnswer(), myQaParis.get(0).getAnswer());
		assertEquals(getQaPair.get(0).getId(), myQaParis.get(0).getId());
	}

	@Test
	@DisplayName("유저의 캐릭터를 업데이트한다")
	public void updateCharacter() {
		//given

		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userTypeRepository.findByName(any())).willReturn(Optional.of(mockUserType));
		given(userUserTypeRepository.findById(any())).willReturn(Optional.ofNullable(mockUserUserType));
		given(userUserTypeRepository.save(any())).willReturn(mockUserUserType);
		//when
		UserUserType userUserType = userUserTypeService.updateCharacter(mockUser.getId(), "JFF", dto);
		//then
		assertEquals(mockUserUserType, userUserType);
	}

	@Test
	@DisplayName("유저의 캐릭터 정보들을 가져온다")
	public void getCharacter() {
		//given
		mockFace = new Face(1L, "얼굴1", null, null);
		mockBody = new Body(2L, "몸2", null, null);
		mockExpression = null;
		mockCharacterSet = null;
		mockUserUserType.updateCharacter(mockFace, mockBody, mockExpression, mockCharacterSet);
		given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
		given(userTypeRepository.findByName(any())).willReturn(Optional.of(mockUserType));
		given(userUserTypeRepository.findById(any())).willReturn(Optional.ofNullable(mockUserUserType));
		given(userUserTypeRepository.findById(any())).willReturn(Optional.ofNullable(mockUserUserType));
		//when
		CharacterItems characterInfoDTO = userUserTypeService.getCharacter(mockUser.getId(), "JFF");
		assertEquals(characterInfoDTO.getFace().getId(), mockFace.getId());
		assertEquals(characterInfoDTO.getFace().getShowCase(), mockFace.getShow_url());
		assertEquals(characterInfoDTO.getFace().getLayoutCase(), mockFace.getLayout_url());
		assertEquals(characterInfoDTO.getBody().getId(), mockBody.getId());
		assertEquals(characterInfoDTO.getBody().getShowCase(), mockBody.getShow_url());
		assertEquals(characterInfoDTO.getBody().getLayoutCase(), mockBody.getLayout_url());
	}

}
