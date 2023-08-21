package com.najasin.domain.character.entity;

import com.najasin.domain.character.dto.response.CharacterItem;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expression_id")
    private Long id;

    @Column(name = "expression_name")
    private String name;

    @Column(name = "expression_show_url",columnDefinition = "TEXT")
    private String show_url;

    @Column(name = "expression_layout_url",columnDefinition = "TEXT")
    private String layout_url;

    public CharacterItem toCharacterItem() {
        return new CharacterItem(id, show_url, layout_url);
    }
}
