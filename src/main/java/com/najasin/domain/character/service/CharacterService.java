package com.najasin.domain.character.service;

import java.util.List;

import com.najasin.domain.character.dto.response.CharacterItem;
import com.najasin.domain.character.dto.response.CharacterItems;
import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.repository.BodyRepository;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.repository.CharacterSetRepository;
import com.najasin.domain.character.repository.ExpressionRepository;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.character.repository.FaceRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterService {
	private final FaceRepository faceRepository;
	private final BodyRepository bodyRepository;
	private final ExpressionRepository expressionRepository;
	private final CharacterSetRepository characterSetRepository;

	@Transactional(readOnly = true)
	public CharacterItems findAllItems() {
		List<CharacterItem> faceList = findFaceAll().stream().map(Face::toCharacterItem).toList();
		List<CharacterItem> bodyList = findBodyAll().stream().map(Body::toCharacterItem).toList();
		List<CharacterItem> expressionList = findExpressionAll().stream().map(Expression::toCharacterItem).toList();
		List<CharacterItem> characterSetList = findCharacterSetAll().stream()
			.map(CharacterSet::toCharacterItem)
			.toList();

		return CharacterItems.builder()
			.face(faceList)
			.body(bodyList)
			.expression(expressionList)
			.set(characterSetList)
			.build();
	}

	@Transactional(readOnly = true)
	public List<Face> findFaceAll() {
		return faceRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Body> findBodyAll() {
		return bodyRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Expression> findExpressionAll() {
		return expressionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<CharacterSet> findCharacterSetAll() {
		return characterSetRepository.findAll();
	}
}
