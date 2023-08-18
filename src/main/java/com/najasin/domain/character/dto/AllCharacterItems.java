package com.najasin.domain.character.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllCharacterItems {
    String baseImage;
    CharacterItems characterItems;

    @Getter
    @Setter
    static public class CharacterItems{
        List<CharacterItem> face = new ArrayList<>();
        List<CharacterItem> body = new ArrayList<>();
        List<CharacterItem> expression = new ArrayList<>();
        List<CharacterItem> set = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CharacterItem{
        Long id;
        String showCase;
        String layoutCase;
    }
}

