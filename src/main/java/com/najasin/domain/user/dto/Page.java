package com.najasin.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.najasin.domain.character.dto.CharacterItems;
import lombok.*;

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
    private List<Questions> questions;
    private List<String> exampleKeywords;
    private Map<String, Integer> originKeywordPercents;
    private Map<String, Long> otherKeywordPercents;


    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class QAPair {
        private Long id;
        private String question;
        private String answer;
        private String nickname;

        public QAPair(Long id, String question, String answer) {
            this.id = id;
            this.question = question;
            this.answer = answer;
            this.nickname = null;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Questions{
        private Long id;
        private String question;
    }
}
