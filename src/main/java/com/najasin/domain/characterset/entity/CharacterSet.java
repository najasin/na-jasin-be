package com.najasin.domain.characterset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "characterSet")
@NoArgsConstructor
public class CharacterSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long id;

    @Column(name = "set_name")
    private String name;

    @Column(name = "set_url")
    private String url;

}