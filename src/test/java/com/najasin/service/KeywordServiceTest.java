package com.najasin.service;

import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.keyword.repository.KeywordRepository;
import com.najasin.domain.keyword.service.KeywordService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {
    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;


    @Test
    @DisplayName("모든 키워드를 가져온다")
    public void getAllKeywords() {
        //given
        List<String> keywords = new ArrayList<>();
        keywords.add("키워드1");
        keywords.add("키워드2");
        keywords.add("키워드3");
        List<Keyword> keywordList = new ArrayList<>();
        keywordList.add(new Keyword(1L, "키워드1"));
        keywordList.add(new Keyword(2L, "키워드2"));
        keywordList.add(new Keyword(3L, "키워드3"));
        given(keywordRepository.findAll()).willReturn(keywordList);
        //when
        List<String> getAllKeywords = keywordService.getAllKeywords();
        //then
        assertEquals(getAllKeywords, keywords);
    }
}
