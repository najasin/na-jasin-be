package com.najasin.domain.user.dto.param;

import java.util.List;
import java.util.Map;

public record CommentParam(String nickname,
						   List<AnswerParam> qas) {

	public static List<CommentParam> ofList(Map<String, List<AnswerParam>> map) {
		return map.keySet().stream().map((key) -> new CommentParam(key, map.get(key))).toList();
	}
}
