package com.najasin.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
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
