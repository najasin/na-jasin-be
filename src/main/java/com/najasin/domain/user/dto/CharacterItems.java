package com.najasin.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterItems {
    private CharacterItem face;
    private CharacterItem body;
    private CharacterItem expression;
    private CharacterItem set;
}
