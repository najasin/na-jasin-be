package com.najasin.domain.character.entity;

import com.najasin.domain.character.dto.response.CharacterItem;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "characterSet")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long id;

    @Column(name = "set_name")
    private String name;

    @Column(name = "set_show_url",columnDefinition = "TEXT")
    private String show_url;

    @Column(name = "set_layout_url",columnDefinition = "TEXT")
    private String layout_url;

    public CharacterItem toCharacterItem() {
        return new CharacterItem(id, show_url, layout_url);
    }
}
