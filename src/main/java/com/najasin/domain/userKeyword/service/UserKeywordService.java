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

import java.util.*;

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
    public Map<String, Integer> getOriginKeywordPercents(String userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Map<String, Integer> originKeywordPercents = new HashMap<>();
        for (UserKeyword UK : user.getUserKeywords()) {
            String keyword = UK.getKeyword().getName();
            Integer originPercent = UK.getOriginPercent();
            originKeywordPercents.put(keyword, originPercent);
        }
        return originKeywordPercents;
    }

    @Transactional
    public Map<String, Long> getOtherKeywordPercents(String userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Map<String, Long> otherKeywordPercents = new HashMap<>();
        for (UserKeyword UK : user.getUserKeywords()) {
            String keyword = UK.getKeyword().getName();
            Long otherPercent = Long.valueOf(UK.getOriginPercent() + UK.getOthersPercent()) / (UK.getOthersCount() + 1);
            otherKeywordPercents.put(keyword, otherPercent);
        }
        return otherKeywordPercents;
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
