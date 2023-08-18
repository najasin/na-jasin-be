package com.najasin.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page {
    private List<String> userTypes;
    private String nickname;
    private String baseImage;
    private CharacterItems characterItems;
    private List<QAPair> myManualQAPair;
    private List<QAPair> othersManualQAPair;
    private List<String> questions;
    private List<String> exampleKeywords;
    private Map<String, Integer> originKeywordPercents;
    private Map<String, Long> otherKeywordPercents;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CharacterItems {
        private CharacterItem face;
        private CharacterItem body;
        private CharacterItem expression;
        private CharacterItem characterSet;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class CharacterItem {
        private Long id;
        private String showCase;
        private String layoutCase;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class QAPair {
        private Long id;
        private String question;
        private String answer;
    }
}
