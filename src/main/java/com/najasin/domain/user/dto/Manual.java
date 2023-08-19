package com.najasin.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.dto.CharacterItems;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manual {
    private List<String> userTypes;
    private String nickname;
    private String baseImage;
    private AllCharacterItems.CharacterItems characterItems;
    private List<QAPair> myManualQAPair;
    private List<OtherManual> othersManualQAPair;
    private List<Page.Questions> questions;
    private List<String> exampleKeywords;
    private Map<String, Integer> originKeywordPercents;
    private Map<String, Long> otherKeywordPercents;
    public Manual() {
        this.userTypes = new ArrayList<>();
        userTypes.add("JFF");
        userTypes.add("DF");
    }

    @Getter
    @Setter
    @Builder
    public static class OtherManual{
        String nickname;
        List<QAPair> qas;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

}
