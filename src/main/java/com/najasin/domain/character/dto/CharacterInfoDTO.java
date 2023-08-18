package com.najasin.domain.character.dto;

import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.face.entity.Face;
import com.najasin.domain.user.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CharacterInfoDTO {
    private Page.CharacterItem face;
    private Page.CharacterItem body;
    private Page.CharacterItem expression;
    private Page.CharacterItem characterSet;
}
