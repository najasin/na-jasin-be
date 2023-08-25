package com.najasin.domain.user.dto.request;

import com.najasin.domain.manual.dto.param.ManualCharacterItems;

public record CharacterUpdateRequest(Long face,
									 Long body,
									 Long expression,
									 Long set) {

	public ManualCharacterItems toManualCharacterItems() {
		return new ManualCharacterItems(face, body, expression, set);
	}
}
