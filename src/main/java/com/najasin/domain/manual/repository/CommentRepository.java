package com.najasin.domain.manual.repository;

import com.najasin.domain.manual.entity.comment.Comment;
import com.najasin.domain.manual.entity.comment.CommentId;
import com.najasin.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
    List<Comment> findAllByUser(User user);
}
