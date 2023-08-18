package com.najasin.domain.character.body.entity;

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

    @Column(name = "body_show_url")
    private String show_url;

    @Column(name = "body_layout_url")
    private String layout_url;
}