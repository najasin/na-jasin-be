package com.najasin.domain.userUserType.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.CharacterInfoDTO;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.user.dto.CharacterItems;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.najasin.domain.userType.service.UserTypeService;


@Service
@RequiredArgsConstructor
public class UserUserTypeService {
    private final UserUserTypeRepository userUserTypeRepository;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final FaceRepository faceRepository;
    private final BodyRepository bodyRepository;
    private final ExpressionRepository expressionRepository;
    private final CharacterSetRepository characterSetRepository;
    private final UserTypeService userTypeService;


    @Transactional
    public List<UserUserType> getUserUserTypesByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return userUserTypeRepository.findALlByUser(user);
    }

    @Transactional
    public UserUserType getUserUserTypeById(String userId, String userTypeName) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserType userType = userTypeRepository.findByName(userTypeName).orElseThrow(EntityNotFoundException::new);
        return userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElse(new UserUserType(user, userType));
    }

    @Transactional
    public List<Page.QAPair> getQAByUserIdAndUserTypeAndQuestionType(String userId, String userTypeName, QuestionType questionType) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        List<Page.QAPair> myQaParis = new ArrayList<>();
        if (questionType == QuestionType.FOR_USER) {
            for (Answer answer : userUserType.getUser().getAnswers()) {
                Page.QAPair qaPair = new Page.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
                myQaParis.add(qaPair);
            }
        }
        else if (questionType == QuestionType.FOR_OTHERS){
            for (Comment comment : userUserType.getUser().getComments()) {
                Page.QAPair qaPair = new Page.QAPair(comment.getQuestion().getId(), comment.getQuestion().getQuestion(), comment.getComment());
                myQaParis.add(qaPair);
            }
        }
        return myQaParis;
    }

    @Transactional
    public UserUserType updateCharacter(String userId, String userTypeName , CharacterItems dto) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        if (dto.getSet()== null) {
            Face face = null; Body body = null; Expression expression = null;
            if (dto.getFace()!=null) face = faceRepository.findById(dto.getFace().getId()).orElse(null);
            if (dto.getBody()!=null) body = bodyRepository.findById(dto.getBody().getId()).orElse(null);
            if (dto.getExpression()!=null) expression = expressionRepository.findById(dto.getExpression().getId()).orElse(null);
            userUserType.updateCharacter(face, body, expression, null);
        }
        else{
            CharacterSet characterSet = characterSetRepository.findById(dto.getSet().getId()).orElseThrow(EntityNotFoundException::new);
            userUserType.updateCharacter(null, null, null, characterSet);
        }
        user.updateUserUserType(userUserType);
        return userUserTypeRepository.save(userUserType);
    }

    @Transactional
    public CharacterInfoDTO getCharacter(String userId, String userTypeName) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        Face face = userUserType.getFace();
        Body body = userUserType.getBody();
        Expression expression = userUserType.getExpression();
        CharacterSet characterSet = userUserType.getSet();
        if (characterSet != null) {
            Page.CharacterItem characterItem = new Page.CharacterItem(characterSet.getId(), characterSet.getUrl(), characterSet.getUrl());
            return new CharacterInfoDTO(null, null, null, characterItem);
        }
        Page.CharacterItem faceItem = (face!=null)? new Page.CharacterItem(face.getId(), face.getShow_url(), face.getLayout_url()):null;
        Page.CharacterItem bodyItem = (body!=null)? new Page.CharacterItem(body.getId(), body.getShow_url(), body.getLayout_url()):null;
        Page.CharacterItem expressionItem = (expression!=null)? new Page.CharacterItem(expression.getId(), expression.getShow_url(), expression.getLayout_url()):null;
        return new CharacterInfoDTO(faceItem, bodyItem, expressionItem, null);
    }

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
