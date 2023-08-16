package com.najasin.domain.keyword.entity;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(name = "keyword_name")
    private String name;

    @OneToMany(mappedBy = "keyword")
    private List<UserKeyword> userKeywords;

    public Keyword(Long id, String name) {
        this.id = id;
        this.name = name;
        this.userKeywords = new ArrayList<>();
    }

}
