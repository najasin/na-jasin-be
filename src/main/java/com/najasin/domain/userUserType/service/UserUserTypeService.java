package com.najasin.domain.userUserType.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.CharacterDTO;
import com.najasin.domain.character.dto.CharacterInfoDTO;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.question.entity.QuestionType;
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

    @Transactional
    public UserUserType updateCharacter(String userId, String userTypeName, CharacterDTO characterDTO) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserType userType = userTypeRepository.findUserTypeByName(userTypeName);
        UserUserType userUserType = userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElseThrow(EntityNotFoundException::new);
        Face face = null; Body body = null; Expression expression = null; CharacterSet characterSet = null;
        if (characterDTO.getCharacterSetID() == null) {
            face = faceRepository.findById(characterDTO.getFaceID()).orElseThrow(EntityNotFoundException::new);
            body = bodyRepository.findById(characterDTO.getBodyID()).orElseThrow(EntityNotFoundException::new);
            expression = expressionRepository.findById(characterDTO.getExpressionID()).orElseThrow(EntityNotFoundException::new);
        }
        else{
            characterSet = characterSetRepository.findById(characterDTO.getCharacterSetID()).orElseThrow(EntityNotFoundException::new);
        }
        userUserType.updateCharacter(face, body, expression, characterSet);
        return userUserType;
    }

    @Transactional
    public UserUserType getUserUserTypeById(String userId, String userTypeName) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserType userType = userTypeRepository.findUserTypeByName(userTypeName);
        return userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<Page.QAPair> getQAByUserIdAndUserTypeAndQuestionType(String userId, String userTypeName, QuestionType questionType) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        List<Page.QAPair> myQaParis = new ArrayList<>();
        for (Answer answer : userUserType.getUser().getAnswers()) {
            if (answer.getQuestion().getQuestionType() == questionType) {
                Page.QAPair qaPair = new Page.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
                myQaParis.add(qaPair);
            }
        }
        return myQaParis;
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
        Page.CharacterItem faceItem = new Page.CharacterItem(face.getId(), face.getShow_url(), face.getLayout_url());
        Page.CharacterItem bodyItem = new Page.CharacterItem(body.getId(), body.getShow_url(), body.getLayout_url());
        Page.CharacterItem expressionItem = new Page.CharacterItem(expression.getId(), expression.getShow_url(), expression.getLayout_url());
        return new CharacterInfoDTO(faceItem, bodyItem, expressionItem, null);
    }
}
