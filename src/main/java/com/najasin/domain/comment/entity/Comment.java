package com.najasin.domain.comment.entity;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.user.entity.User;
import com.najasin.global.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @Column(name = "comment")
    private String comment;

    @Column(name = "nickname")
    private String nickname;

    @Embedded
    private AuditEntity auditEntity;
}
