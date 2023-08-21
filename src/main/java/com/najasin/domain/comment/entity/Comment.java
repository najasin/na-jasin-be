package com.najasin.domain.comment.entity;

import com.najasin.domain.myManual.question.entity.Question;
import com.najasin.domain.user.entity.User;
import com.najasin.global.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
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
