package com.najasin.domain.character.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllCharacterItems {
    String baseImage;
    CharacterItems characterItems;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static public class CharacterItems{
        List<CharacterItem> face = new ArrayList<>();
        List<CharacterItem> body = new ArrayList<>();
        List<CharacterItem> expression = new ArrayList<>();
        List<CharacterItem> set = new ArrayList<>();
    }


}

