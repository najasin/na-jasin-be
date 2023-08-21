package com.najasin.domain.manual.entity.userKeyword;

import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserKeywordId implements Serializable {
    private User user;
    private Keyword keyword;
}
