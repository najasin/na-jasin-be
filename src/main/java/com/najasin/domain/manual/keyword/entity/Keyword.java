package com.najasin.domain.manual.keyword.entity;

import com.najasin.domain.manual.dto.param.JffMyKeyword;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public JffMyKeyword toJffMyKeyword() {
		return new JffMyKeyword(id, name);
	}
}