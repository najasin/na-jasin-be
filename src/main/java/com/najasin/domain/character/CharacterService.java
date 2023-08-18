package com.najasin.domain.character;

import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.expression.repository.ExpressionRepository;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.character.face.repository.FaceRepository;
import com.najasin.domain.user.dto.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final FaceRepository faceRepository;
    private final BodyRepository bodyRepository;
    private final ExpressionRepository expressionRepository;
    private final CharacterSetRepository characterSetRepository;

    @Transactional
    public AllCharacterItems getAllCharacterItems() {
        AllCharacterItems allCharacterItems = new AllCharacterItems();
        allCharacterItems.setBaseImage("기본 이미지 url");
        AllCharacterItems.CharacterItems characterItems = new AllCharacterItems.CharacterItems();
        for (Face face : faceRepository.findAll()) {
            characterItems.getFace().add(new AllCharacterItems.CharacterItem(face.getId(), face.getShow_url(), face.getLayout_url()));
        }
        for (Body body : bodyRepository.findAll()) {
            characterItems.getBody().add(new AllCharacterItems.CharacterItem(body.getId(), body.getShow_url(), body.getLayout_url()));
        }
        for (Expression expression : expressionRepository.findAll()) {
            characterItems.getExpression().add(new AllCharacterItems.CharacterItem(expression.getId(), expression.getShow_url(), expression.getLayout_url()));
        }
        for (CharacterSet characterSet : characterSetRepository.findAll()) {
            characterItems.getSet().add(new AllCharacterItems.CharacterItem(characterSet.getId(), characterSet.getUrl(), characterSet.getUrl()));
        }
        allCharacterItems.setCharacterItems(characterItems);
        return allCharacterItems;
    }
}
