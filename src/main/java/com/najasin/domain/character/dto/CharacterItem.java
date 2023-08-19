package com.najasin.domain.character.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterItem {
    private Long id;
    private String name;
    private String showCase;
    private String layoutCase;
}

