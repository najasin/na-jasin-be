package com.najasin.domain.user.dto.request;

import java.util.List;

import com.najasin.domain.user.dto.param.AnswerUpdateParam;

public record AnswerUpdateRequest(List<AnswerUpdateParam> answers) {
}
