package com.najasin.domain.comment.repository;

import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.comment.entity.CommentId;
import com.najasin.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
    List<Comment> findAllByUser(User user);
}
