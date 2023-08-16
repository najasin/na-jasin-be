package com.najasin.domain.userUserType.service;

import com.najasin.domain.body.entity.Body;
import com.najasin.domain.body.repository.BodyRepository;
import com.najasin.domain.characterset.entity.CharacterSet;
import com.najasin.domain.characterset.repository.CharacterSetRepository;
import com.najasin.domain.dto.CharacterDTO;
import com.najasin.domain.expression.entity.Expression;
import com.najasin.domain.expression.repository.ExpressionRepository;
import com.najasin.domain.face.entity.Face;
import com.najasin.domain.face.repository.FaceRepository;
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

}
