package com.najasin.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Map<String, Integer> otherKeywordPercents;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerDTO {
        private Long id;
        private String answer;
    }
}
