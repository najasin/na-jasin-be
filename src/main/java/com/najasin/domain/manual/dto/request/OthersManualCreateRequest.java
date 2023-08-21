package com.najasin.domain.manual.dto.request;

import java.util.List;

import com.najasin.domain.manual.dto.param.JffCommentParam;
import com.najasin.domain.manual.dto.param.JffKeywordParam;

public record OthersManualCreateRequest(String nickname,
										List<JffCommentParam> answers,
										List<JffKeywordParam> otherKeywordPercents) {
}
