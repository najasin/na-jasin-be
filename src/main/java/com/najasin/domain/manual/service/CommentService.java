package com.najasin.domain.manual.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.entity.comment.Comment;
import com.najasin.domain.manual.repository.CommentRepository;
import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.dto.param.CommentParam;
import com.najasin.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;

	public List<CommentParam> mapToCommentParam(List<Comment> comments) {
		Map<String, List<AnswerParam>> map = new LinkedHashMap<>();

		for(Comment comment : comments) {
			String key = comment.getNickname();
			if(!map.containsKey(comment.getNickname())) {
				map.put(key, new ArrayList<>());
			}
			map.get(key).add(comment.toAnswerParam());
		}

		return  CommentParam.ofList(map);
	}

	@Transactional(readOnly = true)
	public List<Comment> findAllByUserId(String userId) {
		return commentRepository.findAllByUserId(userId);
	}
}
