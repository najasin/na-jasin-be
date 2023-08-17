package com.najasin.domain.comment.repository;

import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.comment.entity.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
