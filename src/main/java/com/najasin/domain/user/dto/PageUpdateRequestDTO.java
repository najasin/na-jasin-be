package com.najasin.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PageUpdateRequestDTO {
    private String nickname;
    private String baseImage;
    private CharacterItems characterItems;
    private List<AnswerDTO> answers;
    private Map<String, Integer> keywordPercents;

    @Getter
    @Setter
    public static class CharacterItems {
        private CharacterItem face;
        private CharacterItem body;
        private CharacterItem expression;
        private CharacterItem set;
    }

    @Getter
    @Setter
    public static class CharacterItem {
        private Long id;
    }

    @Getter
    @Setter
    public static class AnswerDTO {
        private Long id;
        private String answer;
    }
}
