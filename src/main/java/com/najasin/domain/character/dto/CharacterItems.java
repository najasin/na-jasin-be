package com.najasin.domain.character.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterItems {
    private CharacterItem face;
    private CharacterItem body;
    private CharacterItem expression;
    private CharacterItem set;
}
