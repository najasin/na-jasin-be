package com.najasin.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MyPage {
    private List<String> userTypes;
    private String nickname;
    private String baseImage;
    private CharacterItems characterItems;
    private List<QAPair> myManualQAPair;
    private List<QAPair> othersManualQAPair;
    private Map<String, Integer> originKeywordPercents;
    private Map<String, Long> otherKeywordPercents;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CharacterItems {
        private CharacterItem face;
        private CharacterItem body;
        private CharacterItem expression;
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
