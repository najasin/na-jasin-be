package com.najasin.domain.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class KeywordDTO {
    private Long keywordID;
    private int percent;
}
