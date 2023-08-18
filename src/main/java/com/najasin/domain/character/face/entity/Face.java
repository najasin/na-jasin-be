package com.najasin.domain.character.face.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Face {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "face_id")
    private Long id;

    @Column(name = "face_name")
    private String name;

    @Column(name = "face_show_url")
    private String show_url;

    @Column(name = "face_layout_url")
    private String layout_url;
}
