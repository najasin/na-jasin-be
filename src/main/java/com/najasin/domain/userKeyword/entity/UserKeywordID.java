package com.najasin.domain.userKeyword.entity;

import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserKeywordID implements Serializable {
    private User user;
    private Keyword keyword;
}