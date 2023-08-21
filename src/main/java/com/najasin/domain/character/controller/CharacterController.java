package com.najasin.domain.character.controller;

import static com.najasin.global.response.ApiResponse.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.character.dto.message.CharacterItemResponse;
import com.najasin.domain.character.dto.response.CharacterItems;
import com.najasin.domain.character.dto.response.CharacterItemsResponse;
import com.najasin.domain.character.service.CharacterService;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/characterItems")
@RequiredArgsConstructor
public class CharacterController {

	private final CharacterService characterService;
	@Value("${base-image}")
	private String baseImage;

	@GetMapping
	public ResponseEntity<ApiResponse<CharacterItemsResponse>> getCharacterItems() {
		CharacterItems characterItems = characterService.findAllItems();
		return ResponseEntity.ok(createSuccessWithData(
			CharacterItemResponse.FIND_ALL_ITEMS_SUCCESS.getMsg(),
			CharacterItemsResponse.of(baseImage, characterItems)));
	}
}
