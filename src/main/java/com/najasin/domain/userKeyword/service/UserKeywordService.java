package com.najasin.domain.userKeyword.service;

import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.keyword.repository.KeywordRepository;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Role;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.repository.UserKeywordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserKeywordService {
    private final UserKeywordRepository userKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserKeyword save(String userId, Long keywordId, int percent) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Keyword keyword = keywordRepository.findById(keywordId).orElseThrow(EntityNotFoundException::new);
        UserKeyword newUK = new UserKeyword(user, keyword, percent);
        user.updateKeyword(newUK);
        return userKeywordRepository.save(newUK);
    }

    @Transactional
    public UserKeyword updateByOthers(String userId, Long keywordId, int percent) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Keyword keyword = keywordRepository.findById(keywordId).orElseThrow(EntityNotFoundException::new);
        UserKeyword prevUK = null;
        for(UserKeyword userKeyword: user.getUserKeywords()) {
            if (userKeyword.getKeyword().getId() == keywordId) {
                prevUK = userKeyword;
            }
        }
        UserKeyword newUK = new UserKeyword(user, keyword, prevUK.getOriginPercent(), prevUK.getOthersPercent() + percent, prevUK.getOthersCount() + 1);
        user.updateKeyword(newUK);
        return userKeywordRepository.save(newUK);
    }
}
