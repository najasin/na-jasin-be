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
public class Body {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_id")
    private Long id;

    @Column(name = "body_name")
    private String name;

    @Column(name = "body_show_url",columnDefinition = "TEXT")
    private String show_url;

    @Column(name = "body_layout_url",columnDefinition = "TEXT")
    private String layout_url;

    public CharacterItem toCharacterItem() {
        return new CharacterItem(id, show_url, layout_url);
    }
}
