package com.najasin.domain.user.dto.response;

import java.util.List;

import com.najasin.domain.user.dto.param.MyAnswerParam;
import com.najasin.domain.user.dto.param.MyCharacterItemsParam;
import com.najasin.domain.user.dto.param.MyKeywordPercentParam;

import lombok.Builder;

public record MyPageResponse(List<String> userTypes,
							 String nickname,
							 String baseImage,
							 MyCharacterItemsParam characterItems,
							 List<MyAnswerParam> myManualQAPair,
							 List<MyKeywordPercentParam> originKeywordPercents) {

	@Builder
	public MyPageResponse {
	}
}
