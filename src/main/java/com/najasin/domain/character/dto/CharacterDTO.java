package com.najasin.domain.character.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CharacterDTO {
    private Long characterSetID;
    private Long faceID;
    private Long bodyID;
    private Long expressionID;
}
