package com.najasin.domain.manual.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.dto.param.JffMyKeyword;
import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.manual.repository.KeywordRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KeywordService {
	private final KeywordRepository keywordRepository;

	@Transactional(readOnly = true)
	public List<Keyword> findAllByIdList(List<Long> keywordIds) {
		return keywordIds.stream().map(this::findById).toList();
	}

	@Transactional(readOnly = true)
	public Keyword findById(Long id) {
		return keywordRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public List<JffMyKeyword> findAll() {
		return keywordRepository.findAll().stream().map(Keyword::toJffMyKeyword).toList();
	}
}
