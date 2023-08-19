package com.najasin.domain.character;

import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.body.repository.BodyRepository;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.characterset.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.dto.CharacterItem;
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
            characterItems.getFace().add(CharacterItem.builder()
                    .id(face.getId())
                    .showCase(face.getShow_url())
                    .layoutCase(face.getLayout_url())
                    .build());
        }
        for (Body body : bodyRepository.findAll()) {
            characterItems.getBody().add(CharacterItem.builder()
                    .id(body.getId())
                    .showCase(body.getShow_url())
                    .layoutCase(body.getLayout_url())
                    .build());
        }
        for (Expression expression : expressionRepository.findAll()) {
            characterItems.getExpression().add(CharacterItem.builder()
                    .id(expression.getId())
                    .showCase(expression.getShow_url())
                    .layoutCase(expression.getLayout_url())
                    .build());
        }
        for (CharacterSet characterSet : characterSetRepository.findAll()) {
            characterItems.getSet().add(CharacterItem.builder()
                    .id(characterSet.getId())
                    .showCase(characterSet.getUrl())
                    .layoutCase(characterSet.getUrl())
                    .build());
        }
        allCharacterItems.setCharacterItems(characterItems);
        return allCharacterItems;
    }
}
