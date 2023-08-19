package com.najasin.domain.userUserType.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.CharacterItem;
import com.najasin.domain.character.dto.CharacterItems;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.comment.entity.Comment;
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

import java.time.Duration;
import java.time.LocalDateTime;
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
        return userUserTypeRepository.findAllByUser(user);
    }

    @Transactional
    public UserUserType getUserUserTypeById(String userId, String userTypeName) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserType userType = userTypeRepository.findByName(userTypeName).orElseThrow(EntityNotFoundException::new);
        return userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElse(new UserUserType(user, userType));
    }

    @Transactional
    public List<Page.QAPair> getQAByUserIdAndUserTypeForUser(String userId, String userTypeName, QuestionType questionType) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        List<Page.QAPair> myQaParis = new ArrayList<>();
        for (Answer answer : userUserType.getUser().getAnswers()) {
            Page.QAPair qaPair = new Page.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
            myQaParis.add(qaPair);
        }
        return myQaParis;
    }

    @Transactional
    public List<Page.OtherManual> getOtherManualByUserIdAndUserType(String userId, String userTypeName, QuestionType questionType) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        if (userUserType.getUser().getComments().size() == 0) {
            return null;
        }
        List<Page.OtherManual> temp = new ArrayList<>();
        String prevNickname = userUserType.getUser().getComments().get(0).getNickname();
        LocalDateTime prevTime = userUserType.getUser().getComments().get(0).getAuditEntity().getCreatedAt();
        for (Comment comment : userUserType.getUser().getComments()) {
            Page.QAPair qaPair = Page.QAPair.builder()
                    .id(comment.getQuestion().getId())
                    .question(comment.getQuestion().getQuestion())
                    .answer(comment.getComment())
                    .build();
            if (prevNickname.equals(comment.getNickname()) && temp.size() != 0&&Duration.between(prevTime,comment.getAuditEntity().getCreatedAt()).getSeconds()<=10) {
                temp.get(temp.size() - 1).getQas().add(qaPair);
                System.out.println(Duration.between(prevTime,comment.getAuditEntity().getCreatedAt()).getSeconds());

            } else {
                prevNickname = comment.getNickname();
                System.out.println(Duration.between(prevTime,comment.getAuditEntity().getCreatedAt()).getSeconds());
                prevTime = comment.getAuditEntity().getCreatedAt();
                temp.add(Page.OtherManual.builder()
                        .nickname(prevNickname)
                        .qas(new ArrayList<>())
                        .build());
                temp.get(temp.size() - 1).getQas().add(qaPair);
            }
        }
        return temp;
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
    public CharacterItems getCharacter(String userId, String userTypeName) {
        UserUserType userUserType = this.getUserUserTypeById(userId, userTypeName);
        Face face = userUserType.getFace();
        Body body = userUserType.getBody();
        Expression expression = userUserType.getExpression();
        CharacterSet characterSet = userUserType.getSet();

        if (characterSet != null) {
            CharacterItem characterItem = CharacterItem.builder()
                    .id(characterSet.getId())
                    .showCase(characterSet.getUrl())
                    .layoutCase(characterSet.getUrl())
                    .build();
            return new CharacterItems(null, null, null, characterItem);
        }

        CharacterItem faceItem = (face!=null)?
                CharacterItem.builder()
                        .id(face.getId())
                        .showCase(face.getShow_url())
                        .layoutCase(face.getLayout_url())
                        .build() :  null;
        CharacterItem bodyItem = (body!=null)?CharacterItem.builder()
                .id(body.getId())
                .showCase(body.getShow_url())
                .layoutCase(body.getLayout_url())
                .build() :  null;
        CharacterItem expressionItem = (expression!=null)? CharacterItem.builder()
                .id(expression.getId())
                .showCase(expression.getShow_url())
                .layoutCase(expression.getLayout_url())
                .build() : null;
        return new CharacterItems(faceItem, bodyItem, expressionItem, null);
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
