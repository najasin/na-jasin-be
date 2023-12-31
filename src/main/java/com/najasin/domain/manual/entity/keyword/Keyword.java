package com.najasin.domain.manual.entity.keyword;

import com.najasin.domain.manual.dto.param.JffKeywordParam;

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

	public JffKeywordParam toJffMyKeyword() {
		return new JffKeywordParam(id, name);
	}
}
