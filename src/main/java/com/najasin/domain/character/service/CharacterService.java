package com.najasin.domain.character.service;

import java.util.List;

import com.najasin.domain.character.dto.param.CharacterItemParam;
import com.najasin.domain.character.dto.param.CharacterItemsParam;
import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.repository.BodyRepository;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.repository.CharacterSetRepository;
import com.najasin.domain.character.repository.ExpressionRepository;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.character.repository.FaceRepository;

import jakarta.persistence.EntityNotFoundException;
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
	public CharacterItemsParam findAllItems() {
		List<CharacterItemParam> faceList = findFaceAll().stream().map(Face::toCharacterItem).toList();
		List<CharacterItemParam> bodyList = findBodyAll().stream().map(Body::toCharacterItem).toList();
		List<CharacterItemParam> expressionList = findExpressionAll().stream().map(Expression::toCharacterItem).toList();
		List<CharacterItemParam> characterSetList = findCharacterSetAll().stream()
			.map(CharacterSet::toCharacterItem)
			.toList();

		return CharacterItemsParam.builder()
			.face(faceList)
			.body(bodyList)
			.expression(expressionList)
			.set(characterSetList)
			.build();
	}

	@Transactional(readOnly = true)
	public Face findFaceById(Long id) {
		return faceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Body findBodyById(Long id) {
		return bodyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Expression findExpressionById(Long id) {
		return expressionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public CharacterSet findCharacterSetById(Long id) {
		return characterSetRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
